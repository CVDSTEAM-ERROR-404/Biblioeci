package edu.eci.cvds.test;


import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;


import java.util.List;

import static org.junit.Assert.*;


@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CambioEstadoTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public CambioEstadoTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }
	
    @Test
    public void shouldChangeStateOfAvailableToReparableDamage() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
        System.out.println(serviciosBiblioEci.consultarRecurso());
        serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Reparable);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
    }

    @Test
    public void shouldChangeStateOfAvailableToTotalDamage() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
        serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Total);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Total);
    }
	
    @Test
    public void shouldChangeStateOfReparableDamageToAvailable() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
        serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Reparable);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
        serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Disponible);
        resultado = serviciosBiblioEci.consultarRecurso(id);
        assertEquals(resultado.getEstado(), EstadoRecurso.Disponible);
    }
	
    @Test
    public void shouldChangeStateOfReparableDamageToTotalDamage() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        int id = obtenerID();
        serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Reparable);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
        serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Total);
        resultado = serviciosBiblioEci.consultarRecurso(id);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Total);
    }

    @Test
    public void shouldHaveAvailableStateByDefect() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        assertEquals(recurso.getEstado(), EstadoRecurso.Disponible);
    }

    @Test
    public void shouldNotChangeStateOfANullResource(){
        try{
            serviciosBiblioEci.cambiarEstadoRecurso(10, EstadoRecurso.Daño_Reparable);
            fail("Se esperaba fallo porque no se puede cambiar de estado un recurso inexistente");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"No se puede cambiar el estado de un recuso nulo");
        }
    }

    @Test
    public void shouldNotChangeStateOfAResourceWithThatState(){
        try{
            Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            int id = obtenerID();
            serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Disponible);
            Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
            fail("Se esperaba fallo porque no se puede cambiar el estado de un recuso al que tenia anteriormente");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"No se puede cambiar el estado de un recuso al que tenia anteriormente");
        }
    }
	
	@Test
    public void shouldNotChangeStateOfTotalDamageToAvailable(){
		try{
			Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
			serviciosBiblioEci.registrarRecurso(recurso);
            int id = obtenerID();
			serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Total);
			Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
			assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Total);
			serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Disponible);
			resultado = serviciosBiblioEci.consultarRecurso(id);
			assertEquals(resultado.getEstado(), EstadoRecurso.Disponible);
			fail("Se esperaba fallo porque no se puede cambiar de estado un recurso completamente dañado");
		} catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"Un recurso irreparable no puede cambiar su estado");
        }

    }
	
	@Test
    public void shouldNotChangeStateOfTotalDamageToReparableDamage(){
        try{
            Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            int id = obtenerID();
            serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Total);
            Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
            assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Total);
            serviciosBiblioEci.cambiarEstadoRecurso(id, EstadoRecurso.Daño_Reparable);
            resultado = serviciosBiblioEci.consultarRecurso(id);
            assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
            fail("Se esperaba fallo porque no se puede cambiar de estado un recurso completamente dañado");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"Un recurso irreparable no puede cambiar su estado");
        }
    }

    @Test
    public void shouldNotChangeStateToNull(){
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            int id = obtenerID();
            serviciosBiblioEci.cambiarEstadoRecurso(id, null);
            fail("Se esperaba la excepcion por estado nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"No se puede cambiar a un estado nulo");
        }
    }

    private int obtenerID() throws ExcepcionServiciosBiblioEci {
        List<Recurso> recursos = serviciosBiblioEci.consultarRecurso();
        return recursos.get(recursos.size()-1).getID();
    }

}