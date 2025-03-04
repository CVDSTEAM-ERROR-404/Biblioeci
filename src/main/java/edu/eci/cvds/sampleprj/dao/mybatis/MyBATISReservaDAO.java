package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.ReservaDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ReservaMapper;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Evento;
import org.apache.commons.lang3.tuple.MutablePair;

import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.TipoReserva;

import java.util.Date;
import java.util.List;

/**
 * Esta clase conecta los servicios de reservas por medio de la configuracion de MyBATIS
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public class MyBATISReservaDAO implements ReservaDAO {

	@Inject
	private ReservaMapper reservaMapper;

	/**
	 * Consulta las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @return Una lista con las reservas pendientes dentro de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas pendientes
	 */
	@Override
	public List<Reserva> consultarReservasPendientes(long id) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasPendientes(id);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cosultar las reservas futuras ", e);
		}
		return reservas;
	}

	/**
	 * Cancela las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @throws PersistenceException Cuando ocurre algun error al cancelar las reservas pendientes
	 */
	@Override
	public void cancelarReservasPendientes(long id) throws PersistenceException {
		try {
			reservaMapper.cancelarReservasPendientes(id);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cancelar las reservas futuras ", e);
		}
	}

	/**
	 * Registra una nueva reserva de un recurso y un usuario
	 * @param reserva Reserva a registrar
	 */
	@Override
	public void registrarReserva(Reserva reserva) throws PersistenceException {
		try {
			reservaMapper.registrarReserva(reserva);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al registrar la reserva", e);
		}
	}

	/**
	 * Consulta las reservas de la base de datos
	 * @param id El identificador de la reserva
	 * @return Una lista con las reservas de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando courre un error al consultar las reservas
	 */
	@Override
	public Reserva consultarReserva(long id) throws PersistenceException {
		Reserva reserva;
		try {
			reserva = reservaMapper.consultarReserva(id);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cosultar la reserva " + id, e);
		}
		return reserva;
	}

	/**
	 * Consulta todas las reservas de la base de datos
	 * @return Una lista con todas las reservas de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando courre un error al consultar las reservas
	 */
	@Override
	public List<Reserva> consultarReserva() throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReserva();
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cosultar las reservas", e);
		}
		return reservas;
	}

	/**
	 * Retorna una lista de las reservas del recurso especificado
	 * @param recurso Id del recurso especificado
	 * @return Lista de las reservas del recurso
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas
	 */
	@Override
	public List<Reserva> consultarReservasRecurso(long recurso) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasRecurso(recurso);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cosultar las reservas del recurso " + recurso, e);
		}
		return reservas;
	}

	/**
	 * Registra la fecha inicial y final de un semestre
	 * @param fechaInicio Fecha inicial del semestre
	 * @param fechaFinal  Fecha final del semestre
	 * @throws PersistenceException Cuando ocurre algun error al registrar el semestre
	 */
	@Override
	public void registrarSemestre(Date fechaInicio, Date fechaFinal) throws PersistenceException {
		try {
			reservaMapper.registrarSemestre(fechaInicio, fechaFinal);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al registrar el semestre", e);
		}
	}

	/**
	 * Retorna la fecha final e inicial del semestre actual
	 * @return Fecha inicial y final del semestre
	 * @throws PersistenceException Cuando ocurre algun error al consultar las fechas del semestre
	 */
	@Override
	public MutablePair<Date, Date> consultarSemestre() throws PersistenceException {
		MutablePair<Date, Date> semestre;
		try {
			semestre = reservaMapper.consultarSemestre();
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al registrar el semestre", e);
		}
		return semestre;

	}

	/**
	 * Consulta todas las reservas activas del usuario
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas activas del usuario
	 */
	@Override
	public List<Reserva> consultarReservasActivasUsuario(String usuario) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasActivasUsuario(usuario);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar reservas del usuario " + usuario, e);
		}
		return reservas;
	}

	/**
	 * Cambia el estado de la reserva
	 * @param reserva Id de la reserva
	 * @param estado nuevo estado de la reserva
	 * @throws PersistenceException Cuando ocurre algun error al cambiar el estado de la reserva
	 */
	@Override
	public void cambiarEstadoReserva(long reserva, EstadoReserva estado) throws PersistenceException {
		try {
			reservaMapper.cambiarEstadoReserva(reserva, estado);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cambiar el estado de la reserva " + reserva, e);
		}
	}

	/**
	 * Retorna el evento de la reserva que está en curso
	 * @param id Id de la reserva
	 * @return Evento de la reserva en curso
	 * @throws PersistenceException Cuando ocurre algun error al verificar si la reserva esta en curso
	 */
	@Override
	public Evento reservaEnCurso(int id) throws PersistenceException {
		Evento eventoEnCurso;
		try {
			eventoEnCurso = reservaMapper.reservaEnCurso(id);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar si la reserva a cancelar esta en curso", e);
		}
		return eventoEnCurso;

	}

	/**
	 * Consulta todas las reservas pasadas del usuario
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas pasadas del usuario
	 */
	@Override
	public List<Reserva> consultarReservasPasadasUsuario(String usuario) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasPasadasUsuario(usuario);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar las reservas pasadas del usuario " + usuario, e);
		}
		return reservas;
	}

	/**
	 * Consulta todas las reservas canceladas del usuario
	 * @param usuario Id del usuario
	 * @return Todas las reservas del usuario
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas canceladas del usuario
	 */
	@Override
	public List<Reserva> consultarReservasCanceladasUsuario(String usuario) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasCanceladasUsuario(usuario);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar las reservas canceladas del usuario " + usuario, e);
		}
		return reservas;
	}

	/**
	 * Retorna las reservas recurrentes con los filtros especificados
	 * @param tipoReserva Tipo de reserva recurrente
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @param rangoFechas Rango de de fechas en que la reserva puede estar(maxima y minima fecha), es suficiente con que se crucen
	 * @param franja Franja de horarios de los eventos
	 * @return Reservas recurrentes con los filtros dados
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas recurrentes
	 */
	@Override
	public List<Reserva> consultarReservasRecurrentes(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasRecurrentes(tipoReserva, programa, tipoRecurso, rangoFechas, franja);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar las reservas recurrentes", e);
		}
		return reservas;
	}

	/**
	 * Retorna las reservas canceladas con los filtros especificados
	 * @param tipoReserva Tipo de reserva
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @return Reservas canceladas con los filtros dados
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas canceladas
	 */
	@Override
	public List<Reserva> consultarReservasCanceladas(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso) throws PersistenceException {
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasCanceladas(tipoReserva, programa, tipoRecurso);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar las reservas canceladas", e);
		}
		return reservas;
	}

	/**
	 * Retorna las reservas simples con los filtros especificados
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @param rangoFechas Rango de de fechas en que la reserva puede estar(maxima y minima fecha),
	 * es suficiente con que se crucen
	 * @param franja Franja de horarios de los eventos
	 * @return Reservas simples con los filtros dados
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas simples
	 */
	@Override
	public List<Reserva> consultarReservasSimples(String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws PersistenceException{
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasSimples(programa, tipoRecurso, rangoFechas, franja);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar las reservas simples", e);
		}
		return reservas;
	}

	/**
	 * Retorna las reservas activas con los filtros especificados
	 * @param tipoReserva Tipo de reserva
	 * @param programa Programa de la universidad
	 * @param tipoRecurso Tipo de recursos de las reservas a filtrar
	 * @return Reservas activas con los filtros dados
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas activas
	 */
	@Override
	public List<Reserva> consultarReservasActivas(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso) throws PersistenceException{
		List<Reserva> reservas;
		try {
			reservas = reservaMapper.consultarReservasActivas(tipoReserva, programa, tipoRecurso);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar las reservas activas", e);
		}
		return reservas;
	}
}