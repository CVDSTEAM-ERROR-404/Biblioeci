package edu.eci.cvds.test;


import java.text.SimpleDateFormat;
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
public class ServiciosBiblioEciTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public ServiciosBiblioEciTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }


    @Test
    public void shouldRegisterAndConsultAResource() throws ExcepcionServiciosBiblioEci {
		serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5));
		Recurso recurso = serviciosBiblioEci.consultarRecurso(1);
		assertTrue(recurso!=null && recurso.getID()==1);
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


}