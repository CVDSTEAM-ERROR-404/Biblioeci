package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.security.ShiroLogger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;


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
    private Recurso selectedRecurso;
    private TipoReserva tipoReserva;
    private boolean isRecurrente;

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


}
