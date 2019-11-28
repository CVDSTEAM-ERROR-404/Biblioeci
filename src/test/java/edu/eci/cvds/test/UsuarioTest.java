package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import java.util.Date;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest extends ServicioBiblioEciTest{
    public UsuarioTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldConsultAExistentUser() throws ExcepcionServiciosBiblioEci {
        Usuario respuesta = serviciosBiblioEci.consultarUsuario("a@gmail.com");
        assertEquals(usuario,respuesta);
    }

    @Test
    public void shouldReturnNullWhenConsultAUnexistentUser() throws ExcepcionServiciosBiblioEci {
        Usuario usuario = serviciosBiblioEci.consultarUsuario("aa@gmail.com");
        assertNull(usuario);
    }

    @Test
    public void shouldReturnNullWhenConsultAUserWithNullMail() throws ExcepcionServiciosBiblioEci {
        Usuario usuario = serviciosBiblioEci.consultarUsuario(null);
        assertNull(usuario);
    }

    @Test
    public void shouldConsultASchedule() throws ExcepcionServiciosBiblioEci {
        MutablePair semestre = serviciosBiblioEci.consultarSemestreActual();
        assertTrue(semestre!=null && semestre.getLeft()!=null && semestre.getRight()!=null);
    }

    @Test
    public void shouldRegisterASchedule() throws ExcepcionServiciosBiblioEci {
        Date fechaInicial = new Date(119,7,5,7,0,0);
        Date fechaFinal = new Date(120,11,7,13,0,0);
        serviciosBiblioEci.registrarSemestre(fechaInicial,fechaFinal);
        MutablePair semestre = serviciosBiblioEci.consultarSemestreActual();
        assertTrue(semestre.getLeft().equals(fechaInicial) && semestre.getRight().equals(fechaFinal));
    }

    @Test
    public void shouldNotRegisterAScheduleWithoutInitialDate(){
        try {
            serviciosBiblioEci.registrarSemestre(null,new Date());
            fail("Debio fallar por tener una fecha incial nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede registrar un semestre con alguna fecha nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAScheduleWithoutFinalDate(){
        try {
            serviciosBiblioEci.registrarSemestre(new Date(),null);
            fail("Debio fallar por tener una fecha final nula");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede registrar un semestre con alguna fecha nula",e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAScheduleWithFinalDatePreviousInitialDate(){
        Date fechaFinal= new Date(119,7,5,7,0,0);
        Date fechaInicial = new Date(120,11,7,13,0,0);
        try {
            serviciosBiblioEci.registrarSemestre(fechaInicial,fechaFinal);
            fail("Debio fallar por tener una fecha final anterior a la fecha incial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede registrar un semestre con fechas invalidas",e.getMessage());
        }
    }

    @Test
    public void shouldNotRegisterAScheduleWithFinalDateEqualInitialDate(){
        Date fechaInicial = new Date(120,11,7,13,0,0);
        try {
            serviciosBiblioEci.registrarSemestre(fechaInicial,fechaInicial);
            fail("Debio fallar por tener una fecha final igual a la fecha incial");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("No se puede registrar un semestre con fechas invalidas",e.getMessage());
        }
    }
}
