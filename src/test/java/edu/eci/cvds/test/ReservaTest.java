package edu.eci.cvds.test;

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

import static org.junit.Assert.assertEquals;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservaTest {

    private ServiciosBiblioEci serviciosBiblioEci;

    public ReservaTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }

    @Test
    public void shouldNotConsultRervationsOfAnResourceWithoutReservations(){
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        try {
            serviciosBiblioEci.registrarRecurso(recurso);
            int id = obtenerID();
            serviciosBiblioEci.consultarReservasPendientes(id);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsOfAResourceWIthIdZero(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(0);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsOfAResourceWIthNegativeId(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(-1);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultReservationsOfAUnexistentResource(){
        try {
            serviciosBiblioEci.consultarReservasPendientes(100);
        } catch (ExcepcionServiciosBiblioEci e) {
            assertEquals("Error al consultar las reservas futuras",e.getMessage());
        }
    }
    //para las demas pruebas es necesario el metodo para hacer reservas

    private int obtenerID() throws ExcepcionServiciosBiblioEci {
        List<Recurso> recursos = serviciosBiblioEci.consultarRecurso();
        return recursos.get(recursos.size()-1).getID();
    }
}
