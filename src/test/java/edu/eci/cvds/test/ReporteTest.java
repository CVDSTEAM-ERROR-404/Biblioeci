package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReporteTest extends ServicioBiblioEciTest {

    public ReporteTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldConsultTheMostUsedResources() throws ExcepcionServiciosBiblioEci {
        int numReservas = 5;
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        for(int i=0;i<numReservas;i++){
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(i),null,getFinalDateDays(i));
        }
        List<MutablePair<Recurso, Long>> resultados = serviciosBiblioEci.consultarRecursosMasUsados(null,null,null,null);
        long cantidad = 1000;
        for(MutablePair<Recurso, Long> res : resultados){
            assertTrue(res.getRight()<=cantidad);
            cantidad = res.getRight();
        }
    }

    @Test
    public void shouldNotConsultTheMostUsedResourcesWithInvalidHours() {
        try {
            serviciosBiblioEci.consultarRecursosMasUsados(null,new MutablePair(getFinalDate(),getInitialDate()),null,null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Franja Horaria Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheMostUsedResourcesWithInvalidDates() {
        try {
            serviciosBiblioEci.consultarRecursosMasUsados(null,null,new MutablePair(getFinalDate(),getInitialDate()),null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Rango de Fechas Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheLessUsedResources() throws ExcepcionServiciosBiblioEci {
        List<MutablePair<Recurso, Long>> resultados = serviciosBiblioEci.consultarRecursosMenosUsados(null,null,null,null);
        long cantidad = -1;
        for(MutablePair<Recurso, Long> res : resultados){
            assertTrue(res.getRight()>=cantidad);
            cantidad = res.getRight();
        }
    }

    @Test
    public void shouldNotConsultTheLessUsedResourcesWithInvalidHours() {
        try {
            serviciosBiblioEci.consultarRecursosMenosUsados(null,new MutablePair(getFinalDate(),getInitialDate()),null,null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Franja Horaria Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheLessUsedResourcesWithInvalidDates() {
        try {
            serviciosBiblioEci.consultarRecursosMenosUsados(null,null,new MutablePair(getFinalDate(),getInitialDate()),null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Rango de Fechas Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheMostOcupiedSchedule() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDateHoras(2),null,getFinalDate(2));
        List<MutablePair<String,Long>> resultados = serviciosBiblioEci.consultarHorariosMayorOcupacion(null,null,null,null);
        long cantidad = 1000;
        for(MutablePair<String, Long> res : resultados){
            assertTrue(res.getRight()<=cantidad);
            cantidad = res.getRight();
        }
    }

    @Test
    public void shouldNotConsultTheMostOcupiedScheduleWithInvalidHours() {
        try {
            serviciosBiblioEci.consultarHorariosMayorOcupacion(null,new MutablePair(getFinalDate(),getInitialDate()),null,null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Franja Horaria Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheMostOcupiedScheduleWithInvalidDates() {
        try {
            serviciosBiblioEci.consultarHorariosMayorOcupacion(null,null,new MutablePair(getFinalDate(),getInitialDate()),null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Rango de Fechas Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheLessOcupiedSchedule() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDateHoras(2),null,getFinalDate(2));
        List<MutablePair<String,Long>> resultados = serviciosBiblioEci.consultarHorariosMenorOcupacion(null,null,null,null);
        long cantidad = -1;
        for(MutablePair<String, Long> res : resultados){
            assertTrue(res.getRight()>=cantidad);
            cantidad = res.getRight();
        }
    }

    @Test
    public void shouldNotConsultTheLessOcupiedScheduleWithInvalidHours() {
        try {
            serviciosBiblioEci.consultarHorariosMenorOcupacion(null,new MutablePair(getFinalDate(),getInitialDate()),null,null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Franja Horaria Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheLessOcupiedScheduleWithInvalidDates() {
        try {
            serviciosBiblioEci.consultarHorariosMenorOcupacion(null,null,new MutablePair(getFinalDate(),getInitialDate()),null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Rango de Fechas Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheNumberOfRecurrentReservations() throws ExcepcionServiciosBiblioEci {
        MutablePair<Integer,Integer> resultados = serviciosBiblioEci.consultarReservasRecurrentes(null,null,null,null,null);
        assertNotNull(resultados);
    }

    @Test
    public void shouldNotConsultTheNumberOfRecurrentReservationsWithInvalidHours() {
        try {
            serviciosBiblioEci.consultarReservasRecurrentes(null,null,null,null,new MutablePair(getFinalDate(),getInitialDate()));
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Franja Horaria Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheNumberOfRecurrentReservationsWithInvalidDates() {
        try {
            serviciosBiblioEci.consultarReservasRecurrentes(null,null,null,new MutablePair(getFinalDate(),getInitialDate()),null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Rango de Fechas Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheNumberOfCancelledReservations() throws ExcepcionServiciosBiblioEci {
        MutablePair<Integer,Integer> resultados = serviciosBiblioEci.consultarReservasCanceladas(null,null,null,null,null);
        assertNotNull(resultados);
    }

    @Test
    public void shouldNotConsultTheNumberOfCancelledReservationsWithInvalidHours() {
        try {
            serviciosBiblioEci.consultarReservasCanceladas(null,null,null,null,new MutablePair(getFinalDate(),getInitialDate()));
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Franja Horaria Invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheNumberOfCancelledReservationsWithInvalidDates() {
        try {
            serviciosBiblioEci.consultarReservasCanceladas(null,null,null,new MutablePair(getFinalDate(),getInitialDate()),null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Rango de Fechas Invalida",e.getMessage());
        }
    }

}
