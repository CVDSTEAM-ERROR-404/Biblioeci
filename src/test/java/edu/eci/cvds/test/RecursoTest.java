package edu.eci.cvds.test;


import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecursoTest extends ServicioBiblioEciTest{

    public RecursoTest(){super();
    }

    @Test
    public void shouldNotConsultAResourceWithNullId() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = serviciosBiblioEci.consultarRecurso(0);
        assertEquals(recurso,null);
    }

    @Test
    public void shouldConsultOnlyAvailableResources() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
        serviciosBiblioEci.registrarRecurso(new Recurso("incorrecto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5));
        serviciosBiblioEci.cambiarEstadoRecurso(obtenerID(),EstadoRecurso.Daño_Reparable);
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
    public void shouldConsultAvailableResourcesWithCertainCapacity() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 3);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
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
    public void shouldConsultAvailableResourcesWithCertainType() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 3);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
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
        int id = obtenerID();
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
        int id = obtenerID();
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
    public void shouldConsultAvailableResourcesWithCertainUbicationAndType() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
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
    public void shouldConsultAvailableResourcesWithCertainUbicationAndCapacity() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 4);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
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
    public void shouldConsultAvailableResourcesWithCertainConditions() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = new Recurso("correcto", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 2);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
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
    public void shouldNotConsultAvailableResourcesWithNegativeCapacity(){
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,null,null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,UbicacionRecurso.BloqueB,null);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,null,TipoRecurso.SALA_DE_ESTUDIO);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
        try {
            serviciosBiblioEci.consultarRecursosDisponibles(-5,UbicacionRecurso.BloqueB,TipoRecurso.SALA_DE_ESTUDIO);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("La capacidad no puede ser negativa",e.getMessage());
        }
    }


    @Test
    public void shouldNotRegisterANullResource(){
        Recurso recurso = null;
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
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
        int id = obtenerID();
        Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
        assertTrue(resultado!=null && recurso.equals(resultado));
    }

    @Test
    public void shouldReturnNullWhenIdIsGreaterThanAmountOfResources() throws ExcepcionServiciosBiblioEci{
        int id = obtenerID();
        Recurso recurso = serviciosBiblioEci.consultarRecurso(id+1);
        assertEquals(recurso,null);
    }

}