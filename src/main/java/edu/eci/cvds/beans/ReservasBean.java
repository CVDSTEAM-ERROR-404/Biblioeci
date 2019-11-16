package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.security.ShiroLogger;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

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






    public boolean getRecurrente() {
        return isRecurrente;
    }

    public void setRecurrente(boolean recurrente) {
        isRecurrente = recurrente;
    }

    public Recurso getSelectedRecurso() {
        return selectedRecurso;
    }

    public void setSelectedRecurso(Recurso selectedRecurso) {
		
        this.selectedRecurso = selectedRecurso;
		System.out.println(selectedRecurso);
    }

    public TipoReserva getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(TipoReserva tipoReserva) {
        setRecurrente(tipoReserva!=TipoReserva.Simple);
        this.tipoReserva = tipoReserva;
    }

    public TipoReserva[] getTiposReserva(){
        return TipoReserva.values();
    }

    public void registrarReserva(Date fechaInicio, Date fechaFin, Date fechaFinRecurrencia ){
        try{
            Usuario  usuario=serviciosBiblioEci.consultarUsuario(logger.getUser());
            serviciosBiblioEci.registrarReserva(new Reserva(tipoReserva,selectedRecurso,usuario),fechaInicio,fechaFinRecurrencia,fechaFin);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
    }



}
