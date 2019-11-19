package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Reserva;


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
	public List<Reserva> consultarReserva(@Param("id")long id);

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
	 * @param fecha_inicio Fecha inicial del semestre
	 * @param fecha_final Fecha final del semestre
	 */
	public void registrarSemestre(@Param("fecha_inicio") Date fechaInicio,@Param("fecha_final") Date fechaFinal);
}
