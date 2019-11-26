package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.EventoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.EventoMapper;
import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.Evento;

import java.util.Date;
import java.util.List;

public class MyBATISEventoDAO implements EventoDAO {

    @Inject
    private EventoMapper eventoMapper;

    /**
     * Cancela todos los eventos pendientes de una recurso específico
     * 
     * @param idRecurso ID del recurso
     * @throws PersistenceException - Cuando hay errores en la consulta de la base
     *                              datos
     */
    @Override
    public void cancelarEventosPendientesRecurso(long idRecurso) throws PersistenceException {
        try {
            eventoMapper.cancelarEventosPendientesRecurso(idRecurso);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al cancelar los eventos futuros", e);
        }
    }

    /**
     * Registra un nuevo evento de una reserva
     * 
     * @param evento    Evento a registrar
     * @param idReserva id de la Reserva del evento
     * @throws PersistenceException - Cuando hay errores en la consulta de la base
     *                              datos
     */
    @Override
    public void registrarEvento(Evento evento, long idReserva) throws PersistenceException {
        try {
            eventoMapper.registrarEvento(evento, idReserva);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al registrar evento", e);
        }
    }

    /**
     * Consulta todos los eventos
     * 
     * @return Una lista con todos los eventos
     * @throws PersistenceException - Cuando hay errores en la consulta de la base
     *                              datos
     */
    @Override
    public List<Evento> consultarEventos() throws PersistenceException {
        List<Evento> eventos;
        try {
            eventos = eventoMapper.consultarEventos();
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consular eventos", e);
        }
        return eventos;
    }

    /**
     * Consulta todos los eventos de una reserva
     * 
     * @param id El identificador de la reserva
     * @return Una lista con todos los eventos de una reserva
     * @throws PersistenceException - Cuando hay errores en la consulta de la base
     *                              datos
     */
    @Override
    public List<Evento> consultarEventos(int id) throws PersistenceException {
        List<Evento> eventos;
        try {
            eventos = eventoMapper.consultarEventos(id);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consular eventos");
        }
        return eventos;
    }

    /**
     * Retorna todos los eventos que interfieran con el horario pasado en los
     * eventos del recurso
     * 
     * @param recurso     Id del recurso a consultar
     * @param fechaInicio Fecha de inicio de la reserva
     * @param fechaFinal  Fecha de fin de la reserva
     * @return Lista de eventos que interfieran con el horario indicado
     * @throws PersistenceException - Cuando hay errores en la consulta de la base
     *                              datos
     */
    @Override
    public List<Evento> consultarEventosRecurso(long recurso, Date fechaInicio, Date fechaFinal)
            throws PersistenceException {
        List<Evento> eventos;
        try {
            eventos = eventoMapper.consultarEventosRecurso(recurso, fechaInicio, fechaFinal);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar disponibilidad del recurso " + recurso, e);
        }
        return eventos;
    }

    @Override
    public void cambiarEstadoEvento(long evento, EstadoReserva estado) throws PersistenceException {
        try {
            eventoMapper.cambiarEstadoEvento(evento, estado);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al cambiar el estado del evento "+evento, e);
        }
    }

    @Override
    public void cancelarEventosDespues(long reserva, Date fecha) throws PersistenceException {
        try {
            eventoMapper.cancelarEventosDespues(reserva, fecha);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al cancelar los eventos de la reserva" + reserva, e);
        }
    }

    @Override
    public void cancelarEventosReserva(int idReserva) throws PersistenceException {
        try{
            eventoMapper.cancelarEventosReserva(idReserva);
        }
        catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al cancelar los eventos de la reserva" + idReserva, e);
        }

    }

}
