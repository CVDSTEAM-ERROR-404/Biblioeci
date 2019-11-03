package edu.eci.cvds.test;


import static org.junit.Assert.assertTrue;
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
    public void shouldRegisterAResource() throws ExcepcionServiciosBiblioEci {
        //System.out.println(serviciosBiblioEci.consultarRecurso());
		serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", "Sala de estudio", 5,1));
        //System.out.println(serviciosBiblioEci.consultarRecurso());
		Recurso recurso = serviciosBiblioEci.consultarRecurso(1);
        //System.out.println(recurso1);
		assertTrue(recurso!=null);
    }

    @Test
    public void shouldNotRegisterAResourceWithANullName() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso(null, "lugarprueba", "Sala de estudio", 5,2));
            assertTrue(false);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNullUbication() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", null, "Sala de estudio", 5,2));
            assertTrue(false);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldNotRegisterAResourceWithNullType() {
        Recurso recurso2 = null;
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso("prueba", "lugarprueba", null, 5,2));
            assertTrue(false);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            assertTrue(true);
        }
    }


    @After
    public void cerrar(){
        //sqlSession.commit();
        //sqlSession.close();
    }

}