package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.security.ShiroLogger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Esta clase conecta la pagina web con los servicios de reserva de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */
@ManagedBean(name="ReservasBean")
@SessionScoped
public class ReservasBean extends BasePageBean{
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;
    @Inject
    private ShiroLogger logger;
    private Reserva selectedReserva;
    private Recurso selectedRecurso;
    private TipoReserva tipoReserva;
    private boolean isRecurrente;
    private ScheduleModel eventModel=new DefaultScheduleModel();;
    private ScheduleEvent event = new DefaultScheduleEvent();

    public Reserva getSelectedReserva() {
        return selectedReserva;
    }

    public void setSelectedReserva(Reserva selectedReserva) {
        this.selectedReserva = selectedReserva;
    }

    /**
     * Muestra si la reserva es recurrente
     * @return El valor booleano que determina si la reserva es recurrente
     */
    public boolean getRecurrente() {
        return isRecurrente;
    }

    /**
     * Determina si la reserva sera recurrente
     * @param recurrente El valor booleano que determina si la reserva sera recurrente
     */
    public void setRecurrente(boolean recurrente) {
        isRecurrente = recurrente;
    }

    /**
     * Muestra el recurso sobre el cual se hara la reserva
     * @return El recurso sobre el cual se hara la reserva
     */
    public Recurso getSelectedRecurso() {
        return selectedRecurso;
    }

    /**
     * Determina si el recurso sobre el cual se hara la reserva
     * @param selectedRecurso El recurso sobre el cual se hara la reserva
     */
    public void setSelectedRecurso(Recurso selectedRecurso) {
        this.selectedRecurso = selectedRecurso;
    }

    /**
     * Muestra el tipo de la reserva utilizada
     * @return El tipo de la reserva utilizada
     */
    public TipoReserva getTipoReserva() {
        return tipoReserva;
    }

    /**
     * Determina el tipo de la reserva que se va a utilizar
     * @param tipoReserva El tipo de la reserva que se va a utilizar
     */
    public void setTipoReserva(TipoReserva tipoReserva) {
        setRecurrente(tipoReserva!=TipoReserva.Simple);
        this.tipoReserva = tipoReserva;
    }

    /**
     * Muestra todos los tipos de reservas utilizadas
     * @return Una lista con todos los tipos de reservas utilizadas
     */
    public TipoReserva[] getTiposReserva(){
        return TipoReserva.values();
    }

    /**
     * Registra una reserva en la biblioteca
     * @param fechaInicio La fecha incial de la reserva
     * @param fechaFin La fecha final de la reserva
     * @param fechaFinRecurrencia La fecha hasta la cual se realizaran las reservas recurrentes(si no es recurrente usar null)
     */
    public void registrarReserva(Date fechaInicio, Date fechaFin, Date fechaFinRecurrencia ){
        try{
            Usuario  usuario=serviciosBiblioEci.consultarUsuario(logger.getUser());
            ArrayList<Evento>eventosNoRegistrados= serviciosBiblioEci.registrarReserva(new Reserva(tipoReserva,selectedRecurso,usuario),fechaInicio,fechaFinRecurrencia,fechaFin);
            String mensaje="\n";
            if(eventosNoRegistrados!= null && eventosNoRegistrados.size()!=0){
                for(Evento evento :eventosNoRegistrados){
                    mensaje=mensaje.concat("• "+evento.getHoraInicio().toLocaleString()+"-"+evento.getHoraFin().toLocaleString()+"\n");
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El recurso se encontraba ocupado en las siguientes fechas:", mensaje);
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
    }
	
	/**
     * Carga los eventos del calendario de reservas
     */
    public void loadEvents(){
        eventModel = new DefaultScheduleModel();
        try {
            if (selectedRecurso==null){
                setErrorMessage("Para visualizar los horarios primero se debe escoger un recurso");
                FacesContext.getCurrentInstance().getExternalContext().redirect("consultarRecurso.xhtml");
            }
            else {
                List<Reserva> reservas = serviciosBiblioEci.consultarReservasRecurso(getSelectedRecurso().getId());
                for (Reserva reserva : reservas) {
                    for (Evento evento : reserva.getEventosAsignados()) {
                        event = new CustomScheduleEvent("Reserva de " + reserva.getRecurso().getNombre(), evento.getHoraInicio(), evento.getHoraFin(), reserva.getTipo().name(),reserva);

                        eventModel.addEvent(event);
                    }
                }
            }
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            excepcionServiciosBiblioEci.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
     * Obtiene el evento seleccionado en el calendario de reservas
     * @return El evento seleccionado en el calendario de reservas
     */
    public ScheduleEvent getEvent() {
        return event;
    }

	/**
     * Cambia el evento seleccionado en el calendario de reservas
     * @param event El nuevo evento seleccionado en el calendario de reservas
     */
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

	/**
     * Obtiene el modelo del evento seleccionado en el calendario de reservas
     * @return El modelo del evento seleccionado en el calendario de reservas
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

	/**
     * Obtiene el evento de la fecha seleccionada del calendario
     * @param selectEvent El evento seleccionado en el calendario de reservas
     */
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (CustomScheduleEvent) selectEvent.getObject();
        selectedReserva = (Reserva) event.getData();
    }

	/**
     * Redirige a la pagina web del calendario de reservas
     */
    public void redirectHorario() throws ExcepcionServiciosBiblioEci {
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("horario.xhtml");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reserva> consultarReservasActivasUsuario(){
        List<Reserva> reservas = null;
        try {
            reservas =serviciosBiblioEci.consultarReservasActivasUsuario(logger.getUser());
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return reservas;
    }
    public List<Reserva> consultarReservasPasadasUsuario(){
        List<Reserva> reservas = null;
        try {
            reservas =serviciosBiblioEci.consultarReservasPasadasUsuario(logger.getUser());
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return reservas;
    }
    public List<Reserva> consultarReservasCanceladasUsuario(){
        List<Reserva> reservas = null;
        try {
            reservas =serviciosBiblioEci.consultarReservasCanceladasUsuario(logger.getUser());
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return reservas;
    }


}
