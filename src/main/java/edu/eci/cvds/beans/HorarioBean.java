package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Esta clase conecta la pagina web del calendario con el servicio de bibliotecas de la escuela
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */
@ManagedBean(name="HorarioBean")
@ViewScoped
public class HorarioBean extends BasePageBean {
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        try {
            super.init();
           List<Reserva>reservas  =serviciosBiblioEci.consultarReserva(5);
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

}
