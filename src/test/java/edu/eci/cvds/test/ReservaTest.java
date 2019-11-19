package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservaTest extends ServicioBiblioEciTest{

    public ReservaTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldCancelTheFutureReservations() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        serviciosBiblioEci.cancelarReservasPendientes(recurso.getId());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPendientes(recurso.getId());
        List<Reserva> reservasCanceladas = serviciosBiblioEci.consultarReserva(reserva.getId());
        List<Evento> eventoCanceladas = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertEquals(reservas.size(),0);
        for(Reserva res : reservasCanceladas){
            assertEquals(res.getEstado(),EstadoReserva.Cancelada);
        }
        for(Evento eve : eventoCanceladas){
            assertEquals(eve.getEstado(),EstadoReserva.Cancelada);
        }
    }

    @Test
    public void shouldConsultAllReservations() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservas();
        boolean found = false;
        for(Reserva res : reservas){
            if (reserva.equals(res)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultTheFutureReservations() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPendientes(recurso.getId());
        assertTrue(reservas.size()==1 && reservas.get(0).equals(reserva));
    }

    @Test
    public void shouldMakeADailyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(10),getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(eventos.size()>1);
    }

    @Test
    public void shouldMakeAMonthlyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Mensual,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(90),getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(eventos.size()>1);
    }

    @Test
    public void shouldMakeASimpleReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertEquals(1,eventos.size());
    }

    @Test
    public void shouldMakeAWeeklyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Semanal,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(28),getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(eventos.size()>1);
    }

    @Test
    public void shouldReturnAEmptyListWhenConsultReservationsOfAnResourceWithoutReservations() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPendientes(recurso.getId());
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldNotConsultReservationsOfAResourceWIthIdZero(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(0);
            fail("Debio fallar por tener un recurso con id cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El id no puede ser menor que 1",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsOfAResourceWIthNegativeId(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(-1);
            fail("Debio fallar por tener un recurso con id negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El id no puede ser menor que 1",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsOfAUnexistentResource(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(100);
            fail("Debio fallar por tener una id de un recurso que no existe");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso no existe",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsWithANegativeId(){
        try {
            serviciosBiblioEci.consultarReserva(-1);
            fail("Debio fallar por tener una reserva con id negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva con el id -1 es invalido",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsWithIdZero(){
        try {
            serviciosBiblioEci.consultarReserva(0);
            fail("Debio fallar por tener una reserva con id cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva con el id 0 es invalido",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithADurationGreaterThanTwoHours(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate(3));
            fail("Debio fallar por tener una reserva con mas de dos horas de duracion");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Las reservas máximo pueden durar 2 horas",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAnFinalRecurrentDateLessOrEqualThanTheInitialDate(){
        System.out.println("Prueba Reserva Recurrente con fecha fin recurencia igual a fecha inicio");
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getInitialDate(),getFinalDate());
            System.out.println("Id reserva: "+reserva.getId());
            System.out.println("Reservas");
            System.out.println(serviciosBiblioEci.consultarReservas());
            System.out.println("Eventos");
            System.out.println(serviciosBiblioEci.consultarEventos());
            fail("Debio fallar por tener una reserva recurrente cuya recurrencia termina antes o al mismo tiempo que la primera reserva");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser después que la fecha final",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAnInitialDateGreaterThanTheFinalDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getFinalDate(),null,getInitialDate());
            fail("Debio fallar por tener una reserva cuya fecha final era previa a la inicial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser después que la fecha final",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutAResource(){
        Reserva reserva = new Reserva(TipoReserva.Simple,null,usuario);
        try {
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por tener una reserva sin un recurso");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede reservar un recurso nulo",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutFinalDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,null);
            fail("Debio fallar por tener una reserva sin fecha final");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha final no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutInitialDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,null,null,getFinalDate());
            fail("Debio fallar por tener una reserva sin fecha inicial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeASimpleReservationIfTheResourceIsNotAvailable() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por realizar una reserva en un horario no disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso escogido esta ocupado en ese horario",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationIfTheInitialIsDateIsPreviousToNow() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(-3),null,getFinalDate());
            fail("Debio fallar por realizar una reserva con una fecha inicial previa a la fecha actual");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial debe ser despues de la fecha actual",e.getMessage());
        }
    }
	
    @Test
    public void shouldNotMakeARecurrentReservationIfTheResourceIsNotAvailable() throws ExcepcionServiciosBiblioEci {
        System.out.println("Prueba Reserva Recurrente con recurso ocupado");
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(10),getFinalDate());
			System.out.println("Id reserva: "+reserva.getId());
			System.out.println("Reservas Antes");
			System.out.println(serviciosBiblioEci.consultarReservas());
            System.out.println("Eventos Antes");
            System.out.println(serviciosBiblioEci.consultarEventos());
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(10),getFinalDate());
            System.out.println("Reservas Despues");
			System.out.println(serviciosBiblioEci.consultarReservas());
            System.out.println("Eventos Despues");
			System.out.println(serviciosBiblioEci.consultarEventos());
            fail("Debio fallar por realizar una reserva en un horario no disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Todos los horarios de esta reserva estan ocupados actualmente",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutReservationType(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(null,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por tener una reserva sin tipo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al registrar la reserva",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithANullReservation() throws ExcepcionServiciosBiblioEci {
        try {
            serviciosBiblioEci.registrarReserva(null,getInitialDate(),getConcurrentDate(10),getFinalDate());
            fail("Debio fallar por tener una reserva nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede registrar una reserva nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAInitialDatePreviousTheAvailableDateOfTheResource(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"08:00","19:00");
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por tener una reserva de un recurso a una hopra a la que no esta disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso no se puede reservar a esa hora",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAFinalDateAfterTheAvailableDateOfTheResource(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"01:00","07:00");
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por tener una reserva de un recurso a una hopra a la que no esta disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso no se puede reservar a esa hora",e.getMessage());
        }
    }

    @Test
    public void shouldReturnANullListWhenTheReservationDoesntExist() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(100);
        assertEquals(0,reservas.size());
    }
}
