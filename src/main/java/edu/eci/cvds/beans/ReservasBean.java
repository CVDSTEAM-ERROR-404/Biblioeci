package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.*;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.security.ShiroLogger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
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

    @ManagedProperty(value = "#{param.selectedRecurso}")
    private Recurso selectedRecurso;

    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;
    @Inject
    private ShiroLogger logger;


    private TipoReserva tipoReserva;
    private boolean isRecurrente;
    private ScheduleModel eventModel=new DefaultScheduleModel();;
    private ScheduleEvent event = new DefaultScheduleEvent();

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
		this.selectedRecurso = null;
        this.selectedRecurso = selectedRecurso;
        if(this.selectedRecurso == null){
            setErrorMessage("Debes seleccionar un recurso");
        }
		System.out.println(selectedRecurso);
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
            serviciosBiblioEci.registrarReserva(new Reserva(tipoReserva,selectedRecurso,usuario),fechaInicio,fechaFinRecurrencia,fechaFin);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
    }
    public void loadEvents(){
        eventModel = new DefaultScheduleModel();
        try {
            List<Reserva>reservas  =serviciosBiblioEci.consultarReservasPendientes(getSelectedRecurso().getId());
            for(Reserva reserva:reservas){
                for(Evento evento:reserva.getEventosAsignados()){
                    event = new DefaultScheduleEvent("Reserva de "+reserva.getRecurso().getNombre(),evento.getHoraFin(),evento.getHoraFin());
                    eventModel.addEvent(event);
                }
            }
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            excepcionServiciosBiblioEci.printStackTrace();
        }


    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void redirectHorario() throws ExcepcionServiciosBiblioEci {
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/horario.xhtml");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
