package edu.eci.cvds.samples.services.impl;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.HorarioDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.ReservaDAO;

import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;

/**
 * Esta clase implementa los servicios utilizados dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class ServiciosBiblioEciImpl implements ServiciosBiblioEci {
    @Inject
    private RecursoDAO recursoDAO;
    @Inject
    private HorarioDAO horarioDAO;
    @Inject
    private ReservaDAO reservaDAO;

    @Inject
    private UsuarioDAO UsuarioDAO;

    /**
     * Registra un recurso en la base de datos de la biblioteca
     * @param cli El recurso que se va a registrar
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar el recurso, el recurso no posee capacidad valida o el recurso esta vacío
     */
    @Override
    public void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci {
        if(cli==null){throw new ExcepcionServiciosBiblioEci("El recurso a registrar no puede ser nulo");}
        if(cli.getCapacidad()<=0){throw new ExcepcionServiciosBiblioEci("El recurso "+cli.toString()+"tiene una capacidad invalida");}
        try {
            recursoDAO.save(cli);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al guardar el recurso " + cli.toString(), e);
        }

    }

    /**
     * Consulta un recurso en la base de datos de la biblioteca, si no existe retorna null
     * @param id El identificador del recurso
     * @return El recurso de la base de datos
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar el recurso
     */
    @Override
    public Recurso consultarRecurso(long id) throws ExcepcionServiciosBiblioEci {
        Recurso ans = null;
        try {
            ans = recursoDAO.load(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar el recurso " + id, e);
        }
        return ans;
    }

    /**
     * Consulta todos los recursos en la base de datos de la biblioteca
     * @return Una lista con todos los recursos en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar todos los recursos
     */
    @Override
    public List<Recurso> consultarRecurso() throws ExcepcionServiciosBiblioEci {
        List<Recurso> ans= null;
        try {
            ans = recursoDAO.consultarRecursos();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos", e);
        }
        return ans;
    }

    /**
     * Cambia el estado de un recurso en la base de datos
     * @param id El identificador del recurso
     * @param estado El nuevo estado del recurso
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cambiar el estado de un recurso
     */
	@Override
	public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws ExcepcionServiciosBiblioEci{
		try{
		    recursoDAO.cambiarEstadoRecurso(id,estado);
        }catch(PersistenceException e){
            throw new ExcepcionServiciosBiblioEci(e.getMessage(), e);
        }
	}

    /**
     * Cancela las reservas pendientes de un recurso
     * @param id El identificador del recurso
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar las reservas pendientes del recurso
     */
    @Override
    public void cancelarReservasPendientes(int id) throws ExcepcionServiciosBiblioEci {
        try {
            reservaDAO.cancelarReservasPendientes(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al cancelar las reservas futuras");
        }
    }

    /**
     * Consulta las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @return Una lista con todas las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas pendientes de un recurso
     */
    @Override
    public List<Reserva> consultarReservasPendientes(int id) throws ExcepcionServiciosBiblioEci {
        List <Reserva> reservas=null;
        try{
            reservas=reservaDAO.consultarReservasPendientes(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar las reservas futuras");
        }
        return reservas;
    }


}
