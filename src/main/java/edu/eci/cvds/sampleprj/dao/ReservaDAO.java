package edu.eci.cvds.sampleprj.dao;

import java.util.List;
import edu.eci.cvds.samples.entities.Reserva;

/**
 * Esta interfaz conecta los servicios de reservas con su base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */
public interface ReservaDAO{

	/**
	 * Consulta las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @return Una lista con las reservas pendientes dentro de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas pendientes
	 */
	public List<Reserva> consultarReservasPendientes(long id) throws PersistenceException;

	/**
	 * Cancela las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @throws PersistenceException Cuando ocurre algun error al cancelar las reservas pendientes
	 */
	public void cancelarReservasPendientes(long id)throws PersistenceException;

	/**
	 * Registra una nueva reserva de un recurso y un usuario
	 * @param reserva Reserva a registrar
	 * @throws PersistenceException Cuando ocurre algun error al realizar una reserva
	 */
	public void registrarReserva(Reserva reserva)throws PersistenceException;

	/**
	 * Consulta todas las reservas de la base de datos
	 * @return Una lista con todas las reservas de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas
	 */
    public List<Reserva> consultarReserva() throws PersistenceException;

	/**
	 * Consulta una reserva de la base de datos
	 * @return Una lista con la reserva de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando ocurre algun error al consultar la reserva
	 */
	public List<Reserva> consultarReserva(long id) throws PersistenceException;

	/**
	 * Retorna todas las reservas del recurso
	 * @param recurso Id del recurso a filtrar
	 * @return Lista de reservas
	 * @throws PersistenceException
	 */
	public List<Reserva> consultarReservasRecurso(long recurso) throws PersistenceException;

}