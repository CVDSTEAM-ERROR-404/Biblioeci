package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import java.util.List;
import edu.eci.cvds.samples.entities.Reserva;

/**
 * Esta interfaz conecta los servicios de reservas con su base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
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
	 */
	public void registrarReserva(Reserva reserva)throws PersistenceException;
}