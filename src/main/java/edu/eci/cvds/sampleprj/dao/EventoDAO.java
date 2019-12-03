package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.Evento;
import java.util.Date;
import java.util.List;

/**
 * Esta interfaz conecta los servicios relacionados con el evento a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public interface EventoDAO {

    /**
     * Cancela todos los eventos pendientes de una recurso específico
     * @param idRecurso ID del recurso
     * @throws PersistenceException - Cuando hay errores en la consulta de la base de datos
     */
    public void cancelarEventosPendientesRecurso(long idRecurso) throws PersistenceException;

    /**
     * Registra un nuevo evento de una reserva
     * @param evento    Evento a registrar
     * @param idReserva id de la Reserva del evento
     * @throws PersistenceException - Cuando hay errores en la consulta de la base de datos
     */
    public void registrarEvento(Evento evento, long idReserva) throws PersistenceException;

    /**
     * Consulta todos los eventos
     * @return Una lista con todos los eventos
     * @throws PersistenceException - Cuando hay errores en la consulta de la base de datos
     */
    public List<Evento> consultarEventos() throws PersistenceException;

    /**
     * Consulta todos los eventos de una reserva
     * @param id El identificador de la reserva
     * @return Una lista con todos los eventos de una reserva
     * @throws PersistenceException - Cuando hay errores en la consulta de la base de datos
     */
    public List<Evento> consultarEventos(int id) throws PersistenceException;

    /**
     * Registra un evento por medio del estado de este
     * @param estado Estado del evento
     * @param fechaInicio Fecha de inicio del evento
     * @param fechaFin Fecha fin del evento
     * @param reserva Reserva del evento

    public void registrarEventoEstado( String estado, Date fechaInicio , Date fechaFin, Reserva reserva);
     */

    /**
     * Retorna todos los eventos que interfieran con el horario pasado en los eventos del recurso
     * @param recurso     Id del recurso a consultar
     * @param fechaInicio Fecha de inicio de la reserva
     * @param fechaFinal  Fecha de fin de la reserva
     * @return Lista de eventos que interfieran con el horario indicado
     * @throws PersistenceException - Cuando hay errores en la consulta de la base de datos
     */
    public List<Evento> consultarEventosRecurso(long recurso, Date fechaInicio, Date fechaFinal) throws PersistenceException;


    /**
     * Cambia el estado del evento indicado
     * @param evento Id del evento
     * @param estado Estado a actualizar
     * @throws PersistenceException Cuando ocurre un error al actualizar un evento
     */
    public void cambiarEstadoEvento(long evento, EstadoReserva estado) throws PersistenceException;

    /**
     * Cancela todos los eventos de la reserva que inician luego de la fecha dada
     * @param reserva Id de la reserva
     * @param fecha Fecha focal de cancelación
     * @throws PersistenceException Cuando ocurre un error al cancelar los eventos de una fecha dada
     */
    public void cancelarEventosDespues(long reserva, Date fecha) throws PersistenceException;

    /**
     * Cancela todos los eventos de la reserva que aún no han ocurrido
     * @param idReserva Id de la reserva
     * @throws PersistenceException Cuando ocurre un error al cancelar los eventos de una reserva
     */
    public void cancelarEventosReserva(int idReserva) throws PersistenceException;

    /**
     * Consulta todos los eventos activos de una reserva
     * @param reserva El identificador de la reserva
     * @return Una lista con todos los eventos activos de una reserva
     * @throws PersistenceException Cuando ocurre un error al consultar loes eventos activos
     */
    public List<Evento> consultarEventosActivos(int reserva) throws PersistenceException;
}