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
public class CambioEstadoTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public CambioEstadoTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }

    @Test
    public void shouldChangeStateOfAvailableToReparableDamage() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        serviciosBiblioEci.cambiarEstadoRecurso(1, EstadoRecurso.Daño_Reparable);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(1);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
    }

    @Test
    public void shouldChangeStateOfAvailableToTotalDamage() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        serviciosBiblioEci.cambiarEstadoRecurso(1, EstadoRecurso.Daño_Total);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(1);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Total);
    }

    @Test
    public void shouldChangeStateOfReparableDamageToAvailable() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        serviciosBiblioEci.cambiarEstadoRecurso(1, EstadoRecurso.Daño_Reparable);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(1);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
        serviciosBiblioEci.cambiarEstadoRecurso(1, EstadoRecurso.Disponible);
        resultado = serviciosBiblioEci.consultarRecurso(1);
        assertEquals(resultado.getEstado(), EstadoRecurso.Disponible);
    }

    @Test
    public void shouldChangeStateOfReparableDamageToTotalDamage() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
        serviciosBiblioEci.registrarRecurso(recurso);
        serviciosBiblioEci.cambiarEstadoRecurso(1, EstadoRecurso.Daño_Reparable);
        Recurso resultado = serviciosBiblioEci.consultarRecurso(1);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Reparable);
        serviciosBiblioEci.cambiarEstadoRecurso(1, EstadoRecurso.Daño_Total);
        resultado = serviciosBiblioEci.consultarRecurso(1);
        assertEquals(resultado.getEstado(), EstadoRecurso.Daño_Total);
    }

    @Test
    public void shouldNotChangeStateToNull(){
        Recurso recurso = null;
        try {
            recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
            serviciosBiblioEci.registrarRecurso(recurso);
            serviciosBiblioEci.cambiarEstadoRecurso(1, null);
            fail("Se esperaba la excepcion por estado nulo");
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals(e.getMessage(),"Error al cambiar el estado del recurso");
        }

    }

    @Test
    public void shouldHaveAvailableStateByDefect() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
        assertEquals(recurso.getEstado(), EstadoRecurso.Disponible);
    }

    //falta de dañototal a disponible
    //falta de dañototal a reparable


}