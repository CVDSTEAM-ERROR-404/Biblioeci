package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.*;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;


import java.util.Date;

import static org.junit.Assert.assertEquals;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservaTest extends ServicioBiblioEciTest{

    public ReservaTest() {
        super();
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
	/*
    @Test
    public void should() throws ExcepcionServiciosBiblioEci {
        Recurso recurso = new Recurso("prueba", UbicacionRecurso.BloqueB, TipoRecurso.SALA_DE_ESTUDIO, 5);
        Reserva reserva = new Reserva(TipoReserva.Simple,recurso,new Usuario());
        serviciosBiblioEci.registrarRecurso(recurso);
		serviciosBiblioEci.registrarReserva(reserva,new Date(),new Date(),new Date());
        System.out.println(serviciosBiblioEci.consultarEventos());
    }
	*/

    

}
