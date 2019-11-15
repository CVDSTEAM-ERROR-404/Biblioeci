package edu.eci.cvds.samples.services.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.*;

import edu.eci.cvds.samples.entities.*;
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
    private ReservaDAO reservaDAO;
    @Inject
    private UsuarioDAO UsuarioDAO;
    @Inject
    private EventoDAO eventoDAO;

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
		    Recurso recurso = recursoDAO.load(id);
		    if(estado==null){throw new ExcepcionServiciosBiblioEci("No se puede cambiar a un estado nulo");}
            if(recurso==null){throw new ExcepcionServiciosBiblioEci("No se puede cambiar el estado de un recuso nulo");}
            if(recurso.getEstado().equals(estado)){throw new ExcepcionServiciosBiblioEci("No se puede cambiar el estado de un recuso al que tenia anteriormente");}
		    if(recurso.getEstado().equals(EstadoRecurso.Daño_Total)){throw new ExcepcionServiciosBiblioEci("Un recurso irreparable no puede cambiar su estado");}
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
            eventoDAO.cancelarEventosPendientesRecurso(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al cancelar las reservas futuras");
        }
    }

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
     *
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci si se presenta un error en la base de datos al consultar los recursos
     */
    @Override
    public List<Recurso> consultarRecursosDisponibles(int capacidad, UbicacionRecurso ubicacion,TipoRecurso tipo) throws ExcepcionServiciosBiblioEci {
        List<Recurso> recursos = null;
        try{
            recursos=recursoDAO.consultarRecursosDisponibles(capacidad,ubicacion,tipo);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos disponibles");
        }
        return  recursos;
    }

    @Override
    public void registrarReserva(Reserva reserva, Date fechaInicio,Date fechaFinRecurrencia,Date fechaFinEvento) throws ExcepcionServiciosBiblioEci {
        try {
            reservaDAO.registrarReserva(reserva);
            if(reserva.getTipo().equals(TipoReserva.Simple)){
                eventoDAO.registrarEvento(new Evento(fechaInicio,fechaFinEvento),reserva.getId());
            }
            else {
                Calendar inicio=Calendar.getInstance();
                Calendar fin= Calendar.getInstance();
                inicio.setTime(fechaInicio);
                fin.setTime(fechaFinEvento);
                while(inicio.getTime().before(fechaFinRecurrencia)) {
                    eventoDAO.registrarEvento(new Evento(inicio.getTime(),fin.getTime()),reserva.getId());
                    inicio.add(reserva.getTipo().getCalendarConstant(),1);
                    fin.add(reserva.getTipo().getCalendarConstant(),1);
                }
            }
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al registrar la reserva",e);
        }
    }

    @Override
    public List<Evento> consultarEventos() throws ExcepcionServiciosBiblioEci {
        List<Evento> eventos = null;
        try {
            eventos = eventoDAO.consultarEventos();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar eventos",e);
        }
        return eventos;
    }

    @Override
    public List<Evento> consultarEvento(int id) throws ExcepcionServiciosBiblioEci {
        List<Evento> eventos = null;
        if(id<1){throw new ExcepcionServiciosBiblioEci("La reserva con el id "+id+"es invalido");}
        try {
            eventos = eventoDAO.consultarEventos(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar eventos de la reserva"+id);
        }
        return eventos;
    }

    @Override
    public List<Reserva> consultarReservas() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = null;
        try {
            reservas = reservaDAO.consultarReserva();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar las reservas",e);
        }
        return reservas;
    }

    @Override
    public List<Reserva> consultarReserva(long id) throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas = null;
        try {
            reservas = reservaDAO.consultarReserva(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar la reserva"+id,e);
        }
        return reservas;
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
