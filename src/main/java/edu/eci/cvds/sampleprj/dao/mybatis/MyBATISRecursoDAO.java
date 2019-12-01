package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.RecursoMapper;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;

import org.apache.commons.lang3.tuple.MutablePair;
import org.mybatis.guice.transactional.Transactional;

/**
 * Esta clase conecta los servicios de recursos por medio de la configuracion de
 * MyBATIS
 * 
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class MyBATISRecursoDAO implements RecursoDAO {

	@Inject
	private RecursoMapper recursoMapper;

	/**
	 * Carga el recurso a utilizar de la base de datos
	 * 
	 * @param id El identificador del recurso
	 * @return El recurso a utilizar
	 * @throws PersistenceException Cuando ocurre algun error al cargar el Recurso
	 */
	@Override
	public Recurso load(long id) throws PersistenceException {
		Recurso ans;
		try {
			ans = recursoMapper.consultarRecurso(id);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar el recurso " + id, e);
		}
		return ans;
	}

	/**
	 * Guarda un recurso dentro de la base de datos
	 * 
	 * @param rec El recurso a guardar
	 * @throws PersistenceException Cuando ocurre algun error al guardar el Recurso
	 */
	@Transactional
	@Override
	public void save(Recurso rec) throws PersistenceException {
		try {
			recursoMapper.agregarRecurso(rec);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al guardar el recurso " + rec.toString(), e);
		}

	}

	/**
	 * Carga todos los recursos de la base de datos
	 * 
	 * @return Una lista con todos los recursos de la base de datos
	 * @throws PersistenceException Cuando ocurre algun error al consultar los
	 *                              recursos
	 */
	@Override
	public List<Recurso> consultarRecursos() throws PersistenceException {
		List<Recurso> ans;
		try {
			ans = recursoMapper.consultarRecursos();
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos", e);
		}
		return ans;
	}

	/**
	 * Cambia el estado de un recurso dentro de la base de datos
	 * 
	 * @param id     El identificador del recurso
	 * @param estado El nuevo estado del recurso
	 * @throws PersistenceException Cuando ocurre algun error al cambiar el estado o
	 *                              cuando no existe el recurso dentro de la base de
	 *                              datos
	 */
	@Override
	public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws PersistenceException {
		try {
			int rows = recursoMapper.cambiarEstado(id, estado);
			if (rows == 0) {
				throw new PersistenceException("El recurso ingresado no está registrado");
			}
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {

			throw new PersistenceException("Error al cambiar el estado del recurso", e);
		}
	}

	/**
	 * Consulta los recursos dentro de la base de datos de la biblioteca que están
	 * disponibles
	 * 
	 * @param capacidad Filtra por la capacidad de los recursos (Si es 0 no filtra)
	 * @param ubicacion Filtra por la ubicacion de los recursos (Si es null no
	 *                  filtra)
	 * @param tipo      Filtra por el tipo de los recursos (Si es null no filtra)
	 * @return Una lista con los recursos dentro de la base de datos de la
	 *         biblioteca
	 * @throws PersistenceException si se presenta un error en la base de datos al
	 *                              consultar los recursos
	 */
	@Override
	public List<Recurso> consultarRecursosDisponibles(int capacidad, UbicacionRecurso ubicacion, TipoRecurso tipo)
			throws PersistenceException {
		List<Recurso> recursos;
		try {
			recursos = recursoMapper.consultarRecursosDisponibles(capacidad, tipo, ubicacion);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos disponibles", e);
		}
		return recursos;
	}

	@Override
	public List<Recurso> consultarRecursosMasUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria,
			MutablePair<Date, Date> rangoFechas, String programa) throws PersistenceException {
		List<Recurso> recursos;
		try {
			recursos = recursoMapper.consultarRecursosMasUsados(tipo, franjaHoraria, rangoFechas, programa);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos más usados", e);
		}
		return recursos;
	}

	@Override
	public List<Recurso> consultarRecursosMenosUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria,
			MutablePair<Date, Date> rangoFechas, String programa) throws PersistenceException {
		List<Recurso> recursos;
		try {
			recursos = recursoMapper.consultarRecursosMenosUsados(tipo, franjaHoraria, rangoFechas, programa);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos menos usados", e);
		}
		return recursos;
	}

	@Override
	public List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(TipoRecurso tipo,
			MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)
			throws PersistenceException {
		List<MutablePair<String, Long>> horarios = null;
		try {
			horarios = recursoMapper.consultarHorariosMayorOcupacion(tipo, franjaHoraria, rangoFechas, programa);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los horarios de mayor ocupación", e);
		}
		return horarios;
	}

	@Override
	public List<MutablePair<String, Long>> consultarHorariosMenorOcupacion(TipoRecurso tipo,
			MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)
			throws PersistenceException {
		List<MutablePair<String, Long>> horarios = null;
		try {
			horarios = recursoMapper.consultarHorariosMenorOcupacion(tipo, franjaHoraria, rangoFechas, programa);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los horarios de mayor ocupación", e);
		}
		return horarios;
	}

	

}