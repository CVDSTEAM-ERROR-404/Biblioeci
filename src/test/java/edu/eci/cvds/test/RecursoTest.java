package edu.eci.cvds.test;


import java.text.SimpleDateFormat;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import org.junit.Test;
import org.mybatis.guice.transactional.Transactional;
import org.junit.After;

import static org.junit.Assert.*;


@Transactional
public class RecursoTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public RecursoTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }

    @Test
    public void shouldRegisterAndConsultAResource() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
		serviciosBiblioEci.registrarRecurso(recurso);
		int id = serviciosBiblioEci.consultarRecurso().size();
		Recurso resultado = serviciosBiblioEci.consultarRecurso(id);
		assertTrue(recurso!=null && recurso.equals(resultado));
    }

    @Test
    public void shouldNotRegisterAResourceWithANullName() {
        Recurso recurso = null;
        try {
            recurso = new Recurso(null, "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por nombre nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
			assertEquals(e.getMessage(),"Error al guardar el recurso "+recurso);
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
            assertEquals(e.getMessage(),"Error al guardar el recurso "+recurso);
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNullType() {
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", "lugarprueba", null, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por tipo nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"Error al guardar el recurso "+recurso);
        }
    }

	@Test
    public void shouldNotRegisterAResourceWithNegativeCapacity() {
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, -1);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"El recurso "+recurso+"tiene una capacidad invalida");
        }
    }
	

    @Test
    public void shouldNotRegisterAResourceWithNoCapacity() {
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", "lugarprueba",TipoRecurso.SALA_DE_ESTUDIO, 0);
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por capacidad cero");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"El recurso "+recurso+"tiene una capacidad invalida");
        }
    }

    @Test
    public void shouldNotRegisterANullResource(){
        Recurso recurso = null;
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            fail("Se esperaba la excepcion por recurso nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"El recurso a registrar no puede ser nulo");
        }
    }
	
	@Test
    public void shouldReturnNullWhenIdIsGreaterThanAmountOfResources() throws ExcepcionServiciosBiblioEci{
		int id = serviciosBiblioEci.consultarRecurso().size();
        Recurso recurso = serviciosBiblioEci.consultarRecurso(id+1);
		assertEquals(recurso,null);
    }
	
	@Test
    public void shouldNotConsultAResourceWithNullId() throws ExcepcionServiciosBiblioEci{
        Recurso recurso = serviciosBiblioEci.consultarRecurso(0);
		assertEquals(recurso,null);
    }
}