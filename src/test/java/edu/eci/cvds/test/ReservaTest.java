package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReservaTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public ReservaTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }

    @Test
    public void shouldNotConsultRervationsOfAnResourceWithoutReservarions(){
        Recurso recurso = new Recurso("prueba", "lugarprueba", TipoRecurso.SALA_DE_ESTUDIO, 5);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            int id = serviciosBiblioEci.consultarRecurso().size();
            serviciosBiblioEci.consultarReservasPendientes(id);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultRervationsOfAUnexistentResource(){
        try {
            int id = serviciosBiblioEci.consultarRecurso().size();
            serviciosBiblioEci.consultarReservasPendientes(id+1);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultRervationsOfAResourceWIthNegativeId(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(-1);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultRervationsOfAResourceWIthIdZero(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(0);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    //para las demas pruebas es necesario el metodo para hacer reservas
}
