package edu.eci.cvds.test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import org.junit.Test;
import org.mybatis.guice.transactional.Transactional;
import org.junit.After;


@Transactional
public class ServiciosBiblioEciTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public ServiciosBiblioEciTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }


    @Test
    public void shouldRegisterAndConsultAResource() throws ExcepcionServiciosBiblioEci {
		serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", 5,1));
		Recurso recurso = serviciosBiblioEci.consultarRecurso(1);
		assertTrue(recurso!=null && recurso.getID()==1);
    }

    @Test
    public void shouldNotRegisterAResourceWithANullName() {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso(null, "lugarprueba", "Sala de estudio", 5,2));
            fail("Se esperaba la excepcion por nombre nulo");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

    @Test
    public void shouldNotRegisterAResourceWithNullUbication() {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", null, "Sala de estudio", 5,2));
            fail("Se esperaba la excepcion por ubicacion nula");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

    @Test
    public void shouldNotRegisterAResourceWithNullType() {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", null, 5,2));
            fail("Se esperaba la excepcion por tipo nulo");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

	@Test
    public void shouldNotRegisterAResourceWithNegativeCapacity() {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", -1,2));
            fail("Se esperaba la excepcion por capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }
	

    @Test
    public void shouldNotRegisterAResourceWithNoCapacity() {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", 0,2));
            fail("Se esperaba la excepcion por capacidad cero");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }


}