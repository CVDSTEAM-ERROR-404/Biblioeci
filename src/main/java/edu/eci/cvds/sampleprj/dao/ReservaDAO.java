package edu.eci.cvds.sampleprj.dao;

import java.util.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Evento;
import org.apache.commons.lang3.tuple.MutablePair;

import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.TipoReserva;

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
	public Reserva consultarReserva(long id) throws PersistenceException;

	/**
	 * Retorna todas las reservas del recurso
	 * @param recurso Id del recurso a filtrar
	 * @return Lista de reservas
	 * @throws PersistenceException Cuando ocurre algun error consultando las reservas
	 */
	public List<Reserva> consultarReservasRecurso(long recurso) throws PersistenceException;

	/**
	 * Registra las fechas de un semestre
	 * @param fechaInicio Fecha inicial del semestre
	 * @param fechaFinal Fecha final del semestre
	 * @throws PersistenceException Cuando ocurre algun error registrando el semestre
	 */
	public void registrarSemestre(Date fechaInicio, Date fechaFinal) throws PersistenceException;

	/**
	 * Retorna un par de fechas(fecha inicial, fecha final) del semestre
	 * @return Fechas del semestre
	 * @throws PersistenceException Cuando ocurre algun error consultando el semestre
	 */
	public MutablePair<Date, Date> consultarSemestre() throws PersistenceException;

	/**
	 * Retorna todas las reservas activas del usuario
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 * @throws PersistenceException
	 */
	public List<Reserva> consultarReservasActivasUsuario(String usuario) throws PersistenceException;

	/**
	 * Consulta todas las reservas pasadas del usuario 
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 */
	public List<Reserva> consultarReservasPasadasUsuario(String usuario)throws PersistenceException;

	/**
	 * Consulta todas las reservas canceladas del usuario 
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 */
	public List<Reserva> consultarReservasCanceladasUsuario(String usuario) throws PersistenceException;


	/**
	 * Cambia el estado de la reserva
	 * @param reserva Id de la reserva
	 * @param estado nuevo estado de la reserva
	 */
	public void cambiarEstadoReserva(long reserva,EstadoReserva estado) throws PersistenceException;

	public Evento reservaEnCurso(int id) throws PersistenceException;

	/**
	 * Retorna las reservas recurrentes con los filtros especificados
	 * @param tipoReserva Tipo de reserva recurrente
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @param rangoFechas Rango de de fechas en que la reserva puede estar(maxima y minima fecha), 
	 * es suficiente con que se crucen
	 * @param franja Franja de horarios de los eventos
	 * @return Reservas recurrentes con los filtros dados
	 */
	public List<Reserva> consultarReservasRecurrentes(TipoReserva tipoReserva,String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas,MutablePair<Date,Date> franja) throws PersistenceException;

	/**
	 * Retorna las reservas canceladas con los filtros especificados
	 * @param tipoReserva Tipo de reserva recurrente
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @return Reservas canceladas con los filtros dados
	 */
	public List<Reserva> consultarReservasCanceladas(TipoReserva tipoReserva, String programa,TipoRecurso tipoRecurso) throws PersistenceException;
}