package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecursoTest extends ServicioBiblioEciTest{


    public RecursoTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainCapacity() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 3);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(3,null,null);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getCapacidad()==3);
            if(rec.getId()==id){found = true;}
            if(rec.getId()==id+1){fail("No debio encontrar el recurso con otra capacidad");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainConditions() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 2);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.EQUIPO_DE_COMPUTO, 2));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.SALA_DE_ESTUDIO, 5));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.EQUIPO_DE_COMPUTO, 5));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.EQUIPO_DE_COMPUTO, 2));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.SALA_DE_ESTUDIO, 2));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(2,UbicacionRecurso.BloqueB,TipoRecurso.SALA_DE_ESTUDIO);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getUbicacion().equals(UbicacionRecurso.BloqueB) && rec.getCapacidad()==2 && rec.getTipo().equals(TipoRecurso.SALA_DE_ESTUDIO));
            if(rec.getId()==id){found = true;}
            if(rec.getId()>id){fail("No debio encontrar el recurso con otra capacidad u otra ubicacion");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainType() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 3);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.EQUIPO_DE_COMPUTO, 5));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(0,null,TipoRecurso.SALA_DE_ESTUDIO);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getTipo().equals(TipoRecurso.SALA_DE_ESTUDIO));
            if(rec.getId()==id){found = true;}
            if(rec.getId()==id+1){fail("No debio encontrar el recurso con otro tipo");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainTypeAndCapacity() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 6);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.SALA_DE_ESTUDIO, 5));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.EQUIPO_DE_COMPUTO, 6));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(6,null,TipoRecurso.SALA_DE_ESTUDIO);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getTipo().equals(TipoRecurso.SALA_DE_ESTUDIO) && rec.getCapacidad()==6);
            if(rec.getId()==id){found = true;}
            if(rec.getId()>id){fail("No debio encontrar el recurso con otro tipo u otra ubicacion");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainUbication() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.SALA_DE_ESTUDIO, 5));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(0,UbicacionRecurso.BloqueB,null);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getUbicacion().equals(UbicacionRecurso.BloqueB));
            if(rec.getId()==id){found = true;}
            if(rec.getId()==id+1){fail("No debio encontrar el recurso con otra ubicacion");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainUbicationAndCapacity() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 4);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.SALA_DE_ESTUDIO, 4));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(4,UbicacionRecurso.BloqueB,null);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getUbicacion().equals(UbicacionRecurso.BloqueB) && rec.getCapacidad()==4);
            if(rec.getId()==id){found = true;}
            if(rec.getId()>id){fail("No debio encontrar el recurso con otra capacidad u otra ubicacion");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultAvailableResourcesWithCertainUbicationAndType() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueG, TipoRecurso.SALA_DE_ESTUDIO, 5));
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.EQUIPO_DE_COMPUTO, 5));
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(0,UbicacionRecurso.BloqueB,TipoRecurso.SALA_DE_ESTUDIO);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertTrue(rec.getEstado().equals(EstadoRecurso.Disponible) && rec.getTipo().equals(TipoRecurso.SALA_DE_ESTUDIO) && rec.getUbicacion().equals(UbicacionRecurso.BloqueB));
            if(rec.getId()==id){found = true;}
            if(rec.getId()>id){fail("No debio encontrar el recurso con otro tipo u otra ubicacion");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldConsultOnlyAvailableResources() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5));
        serviciosBiblioEci.cambiarEstadoRecurso(id+1,EstadoRecurso.Daño_Reparable);
        List<Recurso> disponibles = serviciosBiblioEci.consultarRecursosDisponibles(0,null,null);
        boolean found = false;
        for(Recurso rec: disponibles){
            assertEquals(rec.getEstado(), EstadoRecurso.Disponible);
            if(rec.getId()==id){found = true;}
            if(rec.getId()==id+1){fail("No debio encontrar el recurso con daño reparable");}
        }
        assertTrue(found);
    }

    @Test
    public void shouldNotConsultAResourceWithNullId() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = serviciosBiblioEci.consultarRecurso(0);
        assertNull(recurso);
    }

    @Test
    public void shouldNotConsultAvailabilityIfTheFinalDateIsNull() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Calendar calendarInicio = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        if(calendarInicio.getTime().getDay()==6){
            calendarInicio.set(year+1900,month,day+2,7,0,0);
        }
        else{
            calendarInicio.set(year+1900,month,day+1,7,0,0);
        }
        Date fechaInicial = calendarInicio.getTime();
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.consultarDisponibilidadRecurso(recurso.getId(),fechaInicial,null);
            fail("Debio fallar por no tener fecha final");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha final no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultAvailabilityIfTheInitialDateIsNull(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarFinal.getTime().getDate();
        int month = calendarFinal.getTime().getMonth();
        int year = calendarFinal.getTime().getYear();
        if(calendarFinal.getTime().getDay()==6){
            calendarFinal.set(year+1900,month,day+2,8,0,0);
        }
        else{
            calendarFinal.set(year+1900,month,day+1,8,0,0);
        }
        Date fechaFinal = calendarFinal.getTime();
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.consultarDisponibilidadRecurso(recurso.getId(),null,fechaFinal);
            fail("Debio fallar por no tener fecha inicial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La fecha inicial no puede ser nula",e.getMessage());
        }
    }

    @Test
    public void shouldReturnAnEmptyListWhenConsultAvailabilityOfAnUnexistentResource(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
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
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.consultarDisponibilidadRecurso(recurso.getId()+1,fechaInicial,fechaFinal);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso no existe",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultAvailabilityOfAResourceWithIdZero(){
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
        try {
            serviciosBiblioEci.consultarDisponibilidadRecurso(0,fechaInicial,fechaFinal);
            fail("Debio fallar porque el recurso no puede tener id cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El id no puede ser menor que 1",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultAvailabilityOfAResourceWithNegativeId(){
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
        try {
            serviciosBiblioEci.consultarDisponibilidadRecurso(-1,fechaInicial,fechaFinal);
            fail("Debio fallar porque el recurso no puede tener id negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El id no puede ser menor que 1",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultAvailableResourcesWithNegativeCapacity(){
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,null,null);
            fail("Debio fallar porque no hay recursos con capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,UbicacionRecurso.BloqueB,null);
            fail("Debio fallar porque no hay recursos con capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,null,TipoRecurso.SALA_DE_ESTUDIO);
            fail("Debio fallar porque no hay recursos con capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,UbicacionRecurso.BloqueB,TipoRecurso.SALA_DE_ESTUDIO);
            fail("Debio fallar porque no hay recursos con capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheSameResource() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        Recurso respuesta = serviciosBiblioEci.consultarRecurso(recurso.getId()-1);
        assertNotEquals(recurso,respuesta);
    }

    @Test
    public void shouldNotRegisterANullResource(){
        try {
            serviciosBiblioEci.registrarRecurso(null);
            fail("Se esperaba la excepcion por recurso nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso a registrar no puede ser nulo",e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithANullName(){
        Recurso recurso = null;
        try {
            recurso = new Recurso(null, UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por nombre nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al guardar el recurso "+recurso,e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNegativeCapacity(){
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, -1);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso "+recurso+"tiene una capacidad invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNoCapacity() {
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", UbicacionRecurso.BloqueB,TipoRecurso.SALA_DE_ESTUDIO, 0);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por capacidad cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("El recurso "+recurso+"tiene una capacidad invalida",e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNullType() {
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, null, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por tipo nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al guardar el recurso "+recurso,e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNullUbication() {
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", null, TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por ubicacion nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al guardar el recurso "+recurso,e.getMessage());
        }
    }

    @Test
    public void shouldRegisterAndConsultAResource() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = recurso.getId();
        Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
        assertTrue(resultado!=null && recurso.equals(resultado));
    }


    @Test
    public void shouldReturnFalseIfTheResourceIsNotAvailable() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
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
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,usuario);
        serviciosBiblioEci.registrarReserva(reserva,fechaInicial,null,fechaFinal);
        assertFalse(serviciosBiblioEci.consultarDisponibilidadRecurso(recurso.getId(),fechaInicial,fechaFinal));
    }


    @Test
    public void shouldReturnNullWhenIdIsGreaterThanAmountOfResources() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = serviciosBiblioEci.consultarRecurso(getLastRecursoId()+1);
        assertNull(recurso);
    }

    @Test
    public void shouldReturnTrueIfTheResourceIsAvailable() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
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
        assertTrue(serviciosBiblioEci.consultarDisponibilidadRecurso(recurso.getId(),fechaInicial,fechaFinal));
    }
}