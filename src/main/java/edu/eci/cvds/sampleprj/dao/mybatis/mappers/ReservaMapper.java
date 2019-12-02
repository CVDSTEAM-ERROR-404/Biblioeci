package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Evento;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.TipoReserva;


/**
 * Esta interfaz conecta los servicios relacionados con las reservas a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */

public interface ReservaMapper {

	/**
	 * Consulta las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @return Una lista con las reservas pendientes dentro de la base de datos de la biblioteca
	 */
	public List<Reserva> consultarReservasPendientes(@Param("id") long id);

	/**
	 * Cancela las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 */
	public void cancelarReservasPendientes(@Param("id")long id);

	/**
	 * Registra una nueva reserva de un recurso y un usuario
	 * @param reserva Reserva a registrar
	 */
	public void registrarReserva(@Param("reserva")Reserva reserva);

	/**
	 * Consulta todas las reservas de la base de datos
	 * @return Una lista con todas las reservas de la base de datos de la biblioteca
	 */
    public List<Reserva> consultarReserva();

	/**
	 * Consulta las reservas de la base de datos
	 * @param id El identificador de la reserva
	 * @return Una lista con las reservas de la base de datos de la biblioteca
	 */
	public Reserva consultarReserva(@Param("id")long id);

	/**
	 * Retorna una lista de las reservas del recurso especificado
	 * @param recurso Id del recurso especificado
	 * @return Lista de las reservas del recurso
	 */
	public List<Reserva> consultarReservasRecurso(@Param("recurso") long recurso);

	/**
	 * Retorna la fecha final e inicial del semestre actual
	 * @return Fecha inicial y final del semestre
	 */
	public MutablePair<Date, Date> consultarSemestre();
	
	/**
	 * Registra la fecha inicial y final de un semestre
	 * @param fechaInicio Fecha inicial del semestre
	 * @param fechaFinal Fecha final del semestre
	 */
	public void registrarSemestre(@Param("fecha_inicio") Date fechaInicio,@Param("fecha_final") Date fechaFinal);

	/**
	 * Consulta todas las reservas activas del usuario 
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 */
	public List<Reserva> consultarReservasActivasUsuario(@Param("usuario") String usuario);

	/**
	 * Consulta todas las reservas pasadas del usuario 
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 */
	public List<Reserva> consultarReservasPasadasUsuario(@Param("usuario") String usuario);

	/**
	 * Consulta todas las reservas canceladas del usuario 
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 */
	public List<Reserva> consultarReservasCanceladasUsuario(@Param("usuario") String usuario);


	/**
	 * Cambia el estado de la reserva
	 * @param reserva Id de la reserva
	 * @param estado nuevo estado de la reserva
	 */
	public void cambiarEstadoReserva(@Param("reserva") long reserva, @Param("estado") EstadoReserva estado);


	/**
	 * Retorna el evento de la reserva que está en curso
	 * @param reserva Id de la reserva
	 * @return Evento de la reserva en curso
	 */
	public Evento reservaEnCurso(@Param("idReserva") long reserva);

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
	public List<Reserva> consultarReservasRecurrentes(@Param("tipoReserva") TipoReserva tipoReserva,@Param("programa") String programa,@Param("tipoRecurso") TipoRecurso tipoRecurso, @Param("rango") MutablePair<Date, Date> rangoFechas,@Param("franja") MutablePair<Date,Date> franja);


	/**
	 * Retorna las reservas simples con los filtros especificados
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @param rangoFechas Rango de de fechas en que la reserva puede estar(maxima y minima fecha), 
	 * es suficiente con que se crucen
	 * @param franja Franja de horarios de los eventos
	 * @return Reservas simples con los filtros dados
	 */
	public List<Reserva> consultarReservasSimples(@Param("programa") String programa,@Param("tipoRecurso") TipoRecurso tipoRecurso, @Param("rango") MutablePair<Date, Date> rangoFechas,@Param("franja") MutablePair<Date,Date> franja);


	/**
	 * Retorna las reservas canceladas con los filtros especificados
	 * @param tipoReserva Tipo de reserva
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @return Reservas canceladas con los filtros dados
	 */
	public List<Reserva> consultarReservasCanceladas(@Param("tipoReserva") TipoReserva tipoReserva,@Param("programa") String programa,@Param("tipoRecurso") TipoRecurso tipoRecurso);


	/**
	 * Retorna las reservas activas con los filtros especificados
	 * @param tipoReserva Tipo de reserva
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @return Reservas activas con los filtros dados
	 */
	public List<Reserva> consultarReservasActivas(@Param("tipoReserva") TipoReserva tipoReserva,@Param("programa") String programa,@Param("tipoRecurso") TipoRecurso tipoRecurso);


	

}
