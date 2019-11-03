package edu.eci.cvds.test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.inject.Inject;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.guice.transactional.Transactional;
import org.junit.After;


@Transactional
public class ServiciosBiblioEciTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosBiblioEci serviciosBiblioEci;

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
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso(null, "lugarprueba", "Sala de estudio", 5,2));
            fail("Se esperaba la excepcion por nombre nulo");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

    @Test
    public void shouldNotRegisterAResourceWithNullUbication() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", null, "Sala de estudio", 5,2));
            fail("Se esperaba la excepcion por ubicacion nula");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

    @Test
    public void shouldNotRegisterAResourceWithNullType() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", null, 5,2));
            fail("Se esperaba la excepcion por tipo nulo");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

	@Test
    public void shouldNotRegisterAResourceWithNegativeCapacity() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", -8,2));
            fail("Se esperaba la excepcion por capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }
	
	@Test
    public void shouldNotRegisterAResourceWithNegativeId() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", 5,-2));
            fail("Se esperaba la excepcion por id negativa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

    @Test
    public void shouldNotRegisterAResourceWithNoCapacity() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", 0,2));
            fail("Se esperaba la excepcion por capacidad negativa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }

    @Test
    public void shouldNotRegisterAResourceWithIdZero() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", 5,0));
            fail("Se esperaba la excepcion por id negativa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {}
    }
    @After
    public void cerrar(){
        //sqlSession.commit();
        //sqlSession.close();
    }

}