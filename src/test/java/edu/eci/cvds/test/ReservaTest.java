package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import java.util.Date;
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
        Reserva res = serviciosBiblioEci.consultarReserva(reserva.getId());
        List<Evento> eventoCanceladas = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertEquals(reservas.size(),0);
        assertEquals(res.getEstado(),EstadoReserva.Cancelada);
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
        Reserva res = serviciosBiblioEci.consultarReserva(reserva.getId());
        assertEquals(res.getId(),reserva.getId());
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(eventos.size()>1);
    }

    @Test
    public void shouldMakeAMonthlyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Mensual,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(90),getFinalDate());
        Reserva res = serviciosBiblioEci.consultarReserva(reserva.getId());
        assertEquals(res.getId(),reserva.getId());
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(eventos.size()>1);
    }

    @Test
    public void shouldMakeASimpleReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        Reserva res = serviciosBiblioEci.consultarReserva(reserva.getId());
        assertEquals(res.getId(),reserva.getId());
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertEquals(1,eventos.size());
    }

    @Test
    public void shouldMakeAWeeklyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Semanal,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(28),getFinalDate());
        Reserva res = serviciosBiblioEci.consultarReserva(reserva.getId());
        assertEquals(res.getId(),reserva.getId());
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
            assertEquals("Las reservas maximo pueden durar 2 horas",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAnFinalRecurrentDateLessOrEqualThanTheInitialDate(){
        System.out.println("Prueba Reserva Recurrente con fecha fin recurencia igual a fecha inicio");
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            Date initialDate = getInitialDate();
            Date recurrentDate = getInitialDate(-1);
            serviciosBiblioEci.registrarReserva(reserva,initialDate,recurrentDate,getFinalDate());
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
    public void shouldNotMakeAReservationWithAInitialDateOutOfTheSemester(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(-1000),null,getFinalDate());
            fail("Debio fallar por tener una reserva cuya fecha inicial no estaba en el semestre");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha de la reserva no esta en el semestre",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAFinalDateOutOfTheSemester(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getInitialDate(2000));
            fail("Debio fallar por tener una reserva cuya fecha final no estaba en el semestre");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha de la reserva no esta en el semestre",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutUser(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,null);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por tener una reserva sin usuario");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El usuario debe estar autenticado para poder reservar",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeARecurrentReservationWithAFinalRecurrentDateOutOfTheSemester(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getInitialDate(2000),getFinalDate());
            fail("Debio fallar por tener una reserva recurrente cuya fecha final de recurrencia no estaba en el semestre");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha de la reserva no esta en el semestre",e.getMessage());
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
    public void shouldNotMakeASimpleReservationIfTheResourceIsNotAvailable() {
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
    public void shouldNotMakeAReservationIfTheInitialIsDateIsPreviousToNow() {
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
    public void shouldNotMakeARecurrentReservationIfTheResourceIsNotAvailable(){
        System.out.println("Prueba Reserva Recurrente con recurso ocupado");
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            Date fechaInicial = getInitialDate();
            Date fechaFinal = getFinalDate();
            Date fechaFinRecurrencia = getConcurrentDate(10);
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,fechaFinRecurrencia,fechaFinal);
			System.out.println("Id reserva: "+reserva.getId());
			System.out.println("Reservas Antes");
			System.out.println(serviciosBiblioEci.consultarReservas());
            System.out.println("Eventos Antes");
            System.out.println(serviciosBiblioEci.consultarEventos());
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,fechaFinRecurrencia,fechaFinal);
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
    public void shouldNotMakeAReservationWithANullReservation(){
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
            fail("Debio fallar por tener una reserva de un recurso a una hora a la que no esta disponible");
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
        Reserva res = serviciosBiblioEci.consultarReserva(100);
        assertEquals(res,null);
    }

    @Test
    public void shouldNotMakeAreservationOfATotalDamageRecourse(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.cambiarEstadoRecurso(recurso.getId(), EstadoRecurso.Daño_Total);
            Recurso nuevoRecurso = serviciosBiblioEci.consultarRecurso(recurso.getId());
            Reserva reserva = new Reserva(TipoReserva.Simple,nuevoRecurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede reservar un recurso que no tenga estado disponible",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAreservationOfAReparableDamageRecourse(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,getInitialDateResource(),getFinalDateResource());
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.cambiarEstadoRecurso(recurso.getId(), EstadoRecurso.Daño_Reparable);
            Recurso nuevoRecurso = serviciosBiblioEci.consultarRecurso(recurso.getId());
            Reserva reserva = new Reserva(TipoReserva.Simple,nuevoRecurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede reservar un recurso que no tenga estado disponible",e.getMessage());
        }
    }
}
