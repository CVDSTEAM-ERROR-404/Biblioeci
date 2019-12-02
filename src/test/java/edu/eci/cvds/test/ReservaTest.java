package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Usuario;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservaTest extends ServicioBiblioEciTest{

    public ReservaTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldCancelTheFutureReservations() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPendientes(recurso.getId());
        assertTrue(reservas.size()==1 && reservas.get(0).equals(reserva));
    }

    @Test
    public void shouldMakeADailyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            Date initialDate = getInitialDate();
            Date recurrentDate = getInitialDate(-1);
            serviciosBiblioEci.registrarReserva(reserva,initialDate,recurrentDate,getFinalDate());
            fail("Debio fallar por tener una reserva recurrente cuya recurrencia termina antes o al mismo tiempo que la primera reserva");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser después que la fecha final",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAnInitialDateGreaterThanTheFinalDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        try {
            Date fechaInicial = getInitialDate();
            Date fechaFinal = getFinalDate();
            Date fechaFinRecurrencia = getConcurrentDate(10);
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,fechaFinRecurrencia,fechaFinal);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,fechaFinRecurrencia,fechaFinal);
            fail("Debio fallar por realizar una reserva en un horario no disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Todos los horarios de esta reserva estan ocupados actualmente",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutReservationType(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
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
        assertNull(res);
    }

    @Test
    public void shouldNotMakeAreservationOfATotalDamageRecourse(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.cambiarEstadoRecurso(recurso.getId(), EstadoRecurso.Daño_Total);
            Recurso nuevoRecurso = serviciosBiblioEci.consultarRecurso(recurso.getId());
            Reserva reserva = new Reserva(TipoReserva.Simple,nuevoRecurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por realizar una reserva de un recurso que no esta disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede reservar un recurso que no tenga estado disponible",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAreservationOfAReparableDamageRecourse(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.cambiarEstadoRecurso(recurso.getId(), EstadoRecurso.Daño_Reparable);
            Recurso nuevoRecurso = serviciosBiblioEci.consultarRecurso(recurso.getId());
            Reserva reserva = new Reserva(TipoReserva.Simple,nuevoRecurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            fail("Debio fallar por realizar una reserva de un recurso que no esta disponible");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede reservar un recurso que no tenga estado disponible",e.getMessage());
        }
    }

    @Test
    public void shouldConsultReservationsByAResource() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(5),getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasRecurso(recurso.getId());
        for(Reserva res : reservas){
            assertEquals(res,reserva);
        }
    }

    @Test
    public void shouldNotConsultReservationsByAResourceWithNegativeId(){
        try {
            serviciosBiblioEci.consultarReservasRecurso(-1);
            fail("Debio fallar por id negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Identificador de recurso invalido",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsByAResourceWithIdZero(){
        try {
            serviciosBiblioEci.consultarReservasRecurso(0);
            fail("Debio fallar por id cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Identificador de recurso invalido",e.getMessage());
        }
    }

    @Test
    public void shouldReturnAnEmptyListWhenConsultReservationsByAUnexistentResource() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasRecurso(100);
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldConsultTheActiveReservationsOfAUser() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(5),getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasActivasUsuario(usuario.getNombre());
        for(Reserva res : reservas){
            assertTrue(res.equals(reserva) && res.getEstado().equals(EstadoReserva.Activa));
        }
    }

    @Test
    public void shouldNotConsultTheActiveReservationsOfANullUser(){
        try {
            serviciosBiblioEci.consultarReservasActivasUsuario(null);
            fail("Debio fallar por corre de usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden consultar reservas de un usuario nulo",e.getMessage());
        }
    }

    @Test
    public void shouldReturnAnEmptyListOfActiveReservationsIfTheUserDoesntExists() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasActivasUsuario("aaa");
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldReturnAnEmptyListIfTheUserExistButDontHaveActiveReservations() throws ExcepcionServiciosBiblioEci {
        Usuario user = serviciosBiblioEci.consultarUsuario("b@gmail.com");
        assertNotNull(user);
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasActivasUsuario("b@gmail.com");
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldNotConsultThePastReservationsOfANullUser(){
        try {
            serviciosBiblioEci.consultarReservasPasadasUsuario(null);
            fail("Debio fallar por corre de usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden consultar reservas de un usuario nulo",e.getMessage());
        }
    }

    @Test
    public void shouldReturnAnEmptyListOfCancelledReservationsIfTheUserDoesntExists() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasCanceladasUsuario("aaa");
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldReturnAnEmptyListIfTheUserExistButDontHaveCancelledReservations() throws ExcepcionServiciosBiblioEci {
        Usuario user = serviciosBiblioEci.consultarUsuario("b@gmail.com");
        assertNotNull(user);
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasCanceladasUsuario("b@gmail.com");
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldNotConsultTheCancelledReservationsOfANullUser(){
        try {
            serviciosBiblioEci.consultarReservasCanceladasUsuario(null);
            fail("Debio fallar por corre de usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden consultar reservas de un usuario nulo",e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheCancelledReservationsOfAUser() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        serviciosBiblioEci.cancelarReservasPendientes(reserva.getRecurso().getId());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasCanceladasUsuario(usuario.getCorreo());
        boolean found = false;
        for(Reserva res : reservas){
            assertEquals(res.getEstado(), EstadoReserva.Cancelada);
            if(res.getId()==reserva.getId()){
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultThePastReservationsOfAUser() throws ExcepcionServiciosBiblioEci {
        Reserva reservaPasada = serviciosBiblioEci.consultarReserva(1);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,serviciosBiblioEci.consultarRecurso(1),usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(5),getFinalDate());
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPasadasUsuario(usuario.getCorreo());
        for(Reserva res : reservas){
            assertTrue(res.equals(reservaPasada) && res.getFechaSolicitud().before(new Date()));
        }
    }

    @Test
    public void shouyldRetutnAnEmptyListOfPastReservationsIfTheUserNotExist() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPasadasUsuario("aaa");
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouyldRetutnAnEmptyListIfTheUserExistButDontHavePastReservations() throws ExcepcionServiciosBiblioEci {
        Usuario user = serviciosBiblioEci.consultarUsuario("b@gmail.com");
        assertNotNull(user);
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPasadasUsuario("b@gmail.com");
        assertEquals(0,reservas.size());
    }

    @Test
    public void shouldNotCancelTheReserveOfANullUser(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
            serviciosBiblioEci.cancelarReserva(nuevaReserva,null);
            fail("Debio fallar por tener un usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El usuario de la reserva no puede ser nulo",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelANullReservation(){
        try {
            serviciosBiblioEci.cancelarReserva(null,usuario);
            fail("Debio fallar por intentar cancelar una reserva nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva a cancelar no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAReservationOfAnotherUser(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
            serviciosBiblioEci.cancelarReserva(nuevaReserva,usuario2);
            fail("Debio fallar por cancelar la reserva de un usuario diferente al que la realizo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden cancelar las reservas de otro usuario",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAReservationThatIsActuallyRunning(){
        Reserva reserva;
        try {
            reserva = serviciosBiblioEci.consultarReserva(2);
            serviciosBiblioEci.cancelarReserva(reserva,usuario);
            fail("Debio fallar porque la reserva esta en curso");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva esta en curso",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAEventOfAReservationThatIsActuallyRunning(){
        Reserva reserva;
        try {
            reserva = serviciosBiblioEci.consultarReserva(2);
            serviciosBiblioEci.cancelarEventoReserva(reserva,usuario,serviciosBiblioEci.consultarEvento(2).get(0));
            fail("Debio fallar porque el evento de la reserva esta en curso");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El evento esta en curso",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAReservationThatAlreadyPassed(){
        Reserva reserva;
        try {
            reserva = serviciosBiblioEci.consultarReserva(1);
            serviciosBiblioEci.cancelarReserva(reserva,usuario);
            fail("Debio fallar porque la reserva ya ocurrio");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden cancelar reservas que ya finalizaron",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAEventOfAReservationThatAlreadyPassed(){
        Reserva reserva;
        try {
            reserva = serviciosBiblioEci.consultarReserva(1);
            serviciosBiblioEci.cancelarEventoReserva(reserva,usuario,serviciosBiblioEci.consultarEvento(1).get(0));
            fail("Debio fallar porque el evento de la reserva ya ocurrio");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El evento ya finalizo",e.getMessage());
        }
    }

    @Test
    public void shouldCancelAReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        serviciosBiblioEci.cancelarReserva(serviciosBiblioEci.consultarReserva(reserva.getId()),usuario);
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasCanceladasUsuario(usuario.getCorreo());
        boolean found = false;
        for(Reserva res : reservas){
            assertEquals(res.getEstado(), EstadoReserva.Cancelada);
            if(res.getId()==reserva.getId()){
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void shouldCancelAEventOfAReservation() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(5),getFinalDate());
        Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(nuevaReserva.getId());
        serviciosBiblioEci.cancelarEventoReserva(nuevaReserva,usuario,eventos.get(0));
        List<Evento> nuevosEventos = serviciosBiblioEci.consultarEvento(nuevaReserva.getId());
        int eventosCancelados = 0;
        boolean found = false;
        for(Evento evento : nuevosEventos){
            if(evento.getEstado().equals(EstadoReserva.Cancelada)){
                eventosCancelados++;
                if(evento.equals(eventos.get(0))){
                    found = true;
                }
            }
        }
        assertEquals(1, eventosCancelados);
        assertTrue(found);
    }

    @Test
    public void shouldCancelAllTheReservationIfTheEventToCancelIsTheOnlyOneAvailable() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(nuevaReserva.getId());
        serviciosBiblioEci.cancelarEventoReserva(nuevaReserva,usuario,eventos.get(0));
        List<Evento> nuevosEventos = serviciosBiblioEci.consultarEvento(nuevaReserva.getId());
        for(Evento evento : nuevosEventos){
            assertEquals(evento.getEstado(),EstadoReserva.Cancelada);
        }
        assertEquals(serviciosBiblioEci.consultarReserva(nuevaReserva.getId()).getEstado(),EstadoReserva.Cancelada);
    }

    @Test
    public void shouldNotCancelAEventOfANullReservation(){
        try {
            serviciosBiblioEci.cancelarEventoReserva(null,usuario,new Evento());
            fail("Debio fallar por intentar cancelar una reserva nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva a cancelar no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAEventOfANullUser(){
        try {
            serviciosBiblioEci.cancelarEventoReserva(new Reserva(),null,new Evento());
            fail("Debio fallar tener un usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El usuario de la reserva no puede ser nulo",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelANullEventOfAResource(){
        try {
            serviciosBiblioEci.cancelarEventoReserva(new Reserva(),usuario,null);
            fail("Debio fallar tener un evento nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El evento a cancelar no puede ser nulo",e.getMessage());
        }
    }

    @Test
    public void shouldNotCancelAEventOfAnotherUser(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
            serviciosBiblioEci.cancelarEventoReserva(nuevaReserva,usuario2,serviciosBiblioEci.consultarEvento(nuevaReserva.getId()).get(0));
            fail("Debio fallar por cancelar la reserva de un usuario diferente al que la realizo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden cancelar las reservas de otro usuario",e.getMessage());
        }
    }
	
	@Test
    public void shouldNotCancelEventosFromADateIfTheUserIsNull(){
        try {
			serviciosBiblioEci.cancelarEventosDespues(new Reserva(),null,new Date());
            fail("Debio fallar tener un usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El usuario de la reserva no puede ser nulo",e.getMessage());
        }
    }
	
	@Test
    public void shouldNotCancelEventosFromADateIfTheReservationIsNull(){
        try {
			serviciosBiblioEci.cancelarEventosDespues(null,usuario,new Date());
            fail("Debio fallar tener un usuario nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva a cancelar no puede ser nula",e.getMessage());
        }
    }
	
	@Test
    public void shouldNotCancelEventosFromANullDate(){
		Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
			serviciosBiblioEci.registrarRecurso(recurso);
			Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
			serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(5),getFinalDate());
			Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
			serviciosBiblioEci.cancelarEventosDespues(nuevaReserva,usuario,null);
            fail("Debio fallar tener la fecha nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha para cancelar eventos no puede ser nula",e.getMessage());
        }
    }
	
	@Test
    public void shouldNotCancelEventosFromAPastDate(){
		Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
			serviciosBiblioEci.registrarRecurso(recurso);
			Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
			serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(5),getFinalDate());
			Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
			serviciosBiblioEci.cancelarEventosDespues(nuevaReserva,usuario,getInitialDate(-20));
            fail("Debio fallar tener la fecha antes a la fecha actual");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden cancelar eventos pasados",e.getMessage());
        }
    }
	
	@Test
    public void shouldNotCancelEventosFromAReservationThatAlreadyPassed(){
        try {
			Reserva reserva = serviciosBiblioEci.consultarReserva(1);
			serviciosBiblioEci.cancelarEventosDespues(reserva,usuario,getInitialDate());
            fail("Debio fallar por intentar cancelar una reserva que ya ocurrio");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La reserva ya finalizo",e.getMessage());
        }
    }
	
	
	@Test
    public void shouldNotCancelAEventFromADateOfAnotherUser(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
            serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
            Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
            serviciosBiblioEci.cancelarEventosDespues(nuevaReserva,usuario2,new Date());
            fail("Debio fallar por cancelar la reserva de un usuario diferente al que la realizo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se pueden cancelar las reservas de otro usuario",e.getMessage());
        }
    }

    @Test
    public void shouldCancelAllTheEventsFromAReservationWithTheActualDate() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"06:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),null,getFinalDate());
        Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
        serviciosBiblioEci.cancelarEventosDespues(nuevaReserva,usuario,getInitialDateHoras(-1));
        Reserva resultado = serviciosBiblioEci.consultarReserva(nuevaReserva.getId());
        assertEquals(resultado.getEstado(),EstadoReserva.Cancelada);
        List<Evento> eventosCancelados = serviciosBiblioEci.consultarEvento(resultado.getId());
        for(Evento evento : eventosCancelados){
            assertEquals(evento.getEstado(),EstadoReserva.Cancelada);
        }
    }

    @Test
    public void shouldCancelSomeEventsFromAReservationWithAFutureDate() throws ExcepcionServiciosBiblioEci {
        int numDias = 5;
        int cantidadCanceladas = 3;
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5,"07:00","19:00");
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,getInitialDate(),getConcurrentDate(numDias),getFinalDate());
        Reserva nuevaReserva = serviciosBiblioEci.consultarReserva(reserva.getId());
        serviciosBiblioEci.cancelarEventosDespues(nuevaReserva,usuario,getInitialDate(cantidadCanceladas-1));
        Reserva resultado = serviciosBiblioEci.consultarReserva(nuevaReserva.getId());
        assertEquals(resultado.getEstado(),EstadoReserva.Activa);
        List<Evento> eventosCancelados = serviciosBiblioEci.consultarEvento(resultado.getId());
        int numCanceladas = 0;
        for(Evento evento : eventosCancelados){
            if(evento.getEstado().equals(EstadoReserva.Cancelada)){
                numCanceladas++;
            }
        }
        assertEquals(numDias-cantidadCanceladas,numCanceladas,1);
    }
}
