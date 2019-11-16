package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.*;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EventoTest extends ServicioBiblioEciTest {

    private Usuario usuario;

    public EventoTest() throws ExcepcionServiciosBiblioEci {
        super();
        usuario = serviciosBiblioEci.consultarUsuario("a@gmail.com");
    }

    @Test
    public void shouldNotConsultAEventWithNegativeId(){
        int id = -1;
        try {
            serviciosBiblioEci.consultarEvento(id);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva con el id "+id+"es invalido",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultAEventWithIdZero(){
        int id = 0;
        try {
            serviciosBiblioEci.consultarEvento(id);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva con el id "+id+"es invalido",e.getMessage());
        }
    }


    @Test
    public void shouldConsultAllEvents() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,7,0,0);
            calendarFinal.set(year+1900,month,day+2,8,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,7,0,0);
            calendarFinal.set(year+1900,month,day+1,8,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
        List<Evento> eventos = serviciosBiblioEci.consultarEventos();
		System.out.println("Todos los eventos");
		System.out.println(eventos);
        List<Evento> eventosReserva = serviciosBiblioEci.consultarEvento(reserva.getId());
		System.out.println("Los eventos de la reserva");
		System.out.println(eventosReserva);
        for(Evento eventores:eventosReserva){
            boolean found = false;
            for(Evento evento:eventos){
                if(evento.equals(eventores)){
					found=true;
					break;
				}
            }
            assertTrue(found);
        }
    }
}
