package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.Test;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventoTest extends ServicioBiblioEciTest {


    public EventoTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldConsultAllEvents() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarRecurso(recurso);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        List<Evento> eventos = serviciosBiblioEci.consultarEventos();
        List<Evento> eventosReserva = serviciosBiblioEci.consultarEvento(reserva.getId());
        for(Evento eventores:eventosReserva){
            boolean found = false;
            for(Evento evento:eventos){
                if (evento.equals(eventores)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    public void shouldNotConsultAEventWithIdZero(){
        int id = 0;
        try {
            serviciosBiblioEci.consultarEvento(id);
            fail("Debio fallar porque no hay eventos con id cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva con el id "+id+" es invalido",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultAEventWithNegativeId(){
        int id = -1;
        try {
            serviciosBiblioEci.consultarEvento(id);
            fail("Debio fallar porque no hay eventos con id negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva con el id "+id+" es invalido",e.getMessage());
        }
    }
}
