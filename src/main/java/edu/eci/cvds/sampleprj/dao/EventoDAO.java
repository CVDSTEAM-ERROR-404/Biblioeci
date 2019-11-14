package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Evento;



public interface EventoDAO {
    /**
     * Cancela todos los eventos pendientes de una recurso específico
     * @param idRecurso ID del recurso
     */
    public void cancelarEventosPendientesRecurso(long idRecurso) throws PersistenceException;

    /**
     * Registra un nuevo evento de una reserva
     * @param evento Evento a registrar
     * @param idReserva id de la Reserva del evento
     */
    public void registrarEvento(Evento evento, long idReserva) throws PersistenceException;

    /**
     * Registra un evento por medio del estado de este
     * @param estado Estado del evento
     * @param fechaInicio Fecha de inicio del evento
     * @param fechaFin Fecha fin del evento
     * @param reserva Reserva del evento

    public void registrarEventoEstado( String estado, Date fechaInicio , Date fechaFin, Reserva reserva);
    */

}
