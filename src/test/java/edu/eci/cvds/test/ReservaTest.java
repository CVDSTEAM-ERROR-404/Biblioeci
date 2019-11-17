package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.*;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import java.util.Calendar;
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
        List<Reserva> reservas = serviciosBiblioEci.consultarReservas();
        boolean found = false;
        for(Reserva res : reservas){
            if(reserva.equals(res)){found=true;}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultTheFutureReservations() throws ExcepcionServiciosBiblioEci {
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
        List<Reserva> reservas = serviciosBiblioEci.consultarReservasPendientes(recurso.getId());
        assertTrue(reservas.size()==1 && reservas.get(0).equals(reserva));
    }

    @Test
    public void shouldMakeADailyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        int numDias = 10;
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
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
        calendarInicio.add(Calendar.DAY_OF_YEAR,numDias);
        serviciosBiblioEci.registrarReserva(reserva,fechaInicial,calendarInicio.getTime(),fechaFinal);
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(numDias-eventos.size()>=0);
    }

    @Test
    public void shouldMakeAMonthlyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        int numMonths = 3;
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Mensual,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,9,0,0);
            calendarFinal.set(year+1900,month,day+2,10,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,9,0,0);
            calendarFinal.set(year+1900,month,day+1,10,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        calendarInicio.add(Calendar.DAY_OF_YEAR,numMonths*30);
        serviciosBiblioEci.registrarReserva(reserva,fechaInicial,calendarInicio.getTime(),fechaFinal);
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertEquals(numMonths,eventos.size());
    }

    @Test
    public void shouldMakeASimpleReservation() throws ExcepcionServiciosBiblioEci {
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
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertEquals(1,eventos.size());
    }

    @Test
    public void shouldMakeAWeeklyRecurrentReservation() throws ExcepcionServiciosBiblioEci {
        int numSemanas = 4;
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Semanal,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,8,0,0);
            calendarFinal.set(year+1900,month,day+2,9,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,8,0,0);
            calendarFinal.set(year+1900,month,day+1,9,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        calendarInicio.add(Calendar.DAY_OF_YEAR,numSemanas*7);
        serviciosBiblioEci.registrarReserva(reserva,fechaInicial,calendarInicio.getTime(),fechaFinal);
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(reserva.getId());
        for(Reserva res:reservas){
            assertEquals(res.getId(),reserva.getId());
        }
        List<Evento> eventos = serviciosBiblioEci.consultarEvento(reserva.getId());
        assertTrue(numSemanas-eventos.size()>=0);
    }

    @Test
    public void shouldReturnAEmptyListWhenConsultReservationsOfAnResourceWithoutReservations() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
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
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,12,0,0);
            calendarFinal.set(year+1900,month,day+2,15,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,12,0,0);
            calendarFinal.set(year+1900,month,day+1,15,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
            fail("Debio fallar por tener una reserva con mas de dos horas de duracion");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Las reservas máximo pueden durar 2 horas",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAnFinalRecurrentDateLessOrEqualThanTheInitialDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(TipoReserva.Recurrente_Diaria,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,12,0,0);
            calendarFinal.set(year+1900,month,day+2,14,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,12,0,0);
            calendarFinal.set(year+1900,month,day+1,14,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,fechaInicial,fechaFinal);
            fail("Debio fallar por tener una reserva recurrente cuya recurrencia termina antes o al mismo tiempo que la primera reserva");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser después que la fecha final",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithAnInitialDateGreaterThanTheFinalDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,12,0,0);
            calendarFinal.set(year+1900,month,day+2,14,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,12,0,0);
            calendarFinal.set(year+1900,month,day+1,14,0,0);
        }
        Date fechaInicial = calendarFinal.getTime();
        Date fechaFinal = calendarInicio.getTime();
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
            fail("Debio fallar por tener una reserva cuya fecha final era previa a la inicial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser después que la fecha final",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutAResource(){
        Recurso recurso = null;
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,11,0,0);
            calendarFinal.set(year+1900,month,day+2,12,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,11,0,0);
            calendarFinal.set(year+1900,month,day+1,12,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        try {
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
            fail("Debio fallar por tener una reserva sin un recurso");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al registrar la reserva",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutFinalDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        Date fechaInicial = new Date(119,10,21,8,0,0);
        Date fechaFinal = null;
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
            fail("Debio fallar por tener una reserva sin fecha final");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha final no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutInitialDate(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        Date fechaInicial = null;
        Date fechaFinal = new Date(119,10,21,8,0,0);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
            fail("Debio fallar por tener una reserva sin fecha inicial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotMakeAReservationWithoutReservationType(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(null,recurso,usuario);
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,11,0,0);
            calendarFinal.set(year+1900,month,day+2,12,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,11,0,0);
            calendarFinal.set(year+1900,month,day+1,12,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        Date fechaFinal = calendarFinal.getTime();
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
            fail("Debio fallar por tener una reserva sin tipo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al registrar la reserva",e.getMessage());
        }
    }

    @Test
    public void shouldReturnANullListWhenTheReservationDoesntExist() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = serviciosBiblioEci.consultarReserva(100);
        assertEquals(0,reservas.size());
    }
}
