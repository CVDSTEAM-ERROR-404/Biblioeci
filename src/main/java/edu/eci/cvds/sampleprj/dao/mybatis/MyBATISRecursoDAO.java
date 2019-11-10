package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.RecursoMapper;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.TipoRecurso;
import org.mybatis.guice.transactional.Transactional;

/**
 * Esta clase conecta los servicios de recursos por medio de la configuracion de MyBATIS
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class MyBATISRecursoDAO implements RecursoDAO {

	@Inject
	private RecursoMapper recursoMapper;

	/**
	 * Carga el recurso a utilizar de la base de datos
	 * @param id El identificador del recurso
	 * @return El recurso a utilizar
	 * @throws PersistenceException Cuando ocurre algun error al cargar el Recurso
	 */
	@Override
	public Recurso load(long id) throws PersistenceException {
		Recurso ans=null;
		try {
			ans=recursoMapper.consultarRecurso(id);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar el recurso " + id, e);
		}
		return ans;
	}

	/**
	 * Guarda un recurso dentro de la base de datos
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
	 * @return Una lista con todos los recursos de la base de datos
	 * @throws PersistenceException Cuando ocurre algun error al consultar los recursos
	 */
	@Override
	public List<Recurso> consultarRecursos() throws PersistenceException {
		List<Recurso> ans = null;
		try {
			ans=recursoMapper.consultarRecursos();
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos", e);
		}
		return ans;
	}

	/**
	 * Cambia el estado de un recurso dentro de la base de datos
	 * @param id El identificador del recurso
	 * @param estado El nuevo estado del recurso
	 * @throws PersistenceException Cuando ocurre algun error al cambiar el estado o cuando no existe el recurso dentro de la base de datos
	 */
	@Override
	public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws PersistenceException{
		try{
			int rows = recursoMapper.cambiarEstado(id,estado);
			if (rows==0){
				throw new PersistenceException("El recurso ingresado no está registrado");
			}
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e) {

			throw new PersistenceException("Error al cambiar el estado del recurso", e);
		}
	}

	/**
	 * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
	 *
	 * @return Una lista con los recursos dentro de la base de datos de la biblioteca
	 * @throws PersistenceException si se presenta un error en la base de datos al consultar los recursos
	 */
	@Override
	public List<Recurso> consultarRecursosDisponibles() throws PersistenceException {
		List<Recurso> recursos=null;
		try{
			recursos=recursoMapper.consultarRecursosDisponibles();
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos disponibles", e);
		}
		return recursos;
	}

	/**
	 * Consulta los recursos dentro de la base de datos de la biblioteca, filtrados por tipo y disponiles
	 *
	 * @param tipo Tipo para la filtrar los recursos
	 * @return Una lista con los recursos dentro de la base de datos de la biblioteca
	 * @throws PersistenceException si se presenta un error en la base de datos al consultar los recursos
	 */
	@Override
	public List<Recurso> consultarRecursosDisponibles(TipoRecurso tipo) throws PersistenceException {
		List<Recurso> recursos=null;
		try{
			recursos=recursoMapper.consultarRecursosDisponibles(tipo);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos disponibles filtrados por tipo", e);
		}
		return recursos;
	}

	/**
	 * Consulta los recursos dentro de la base de datos de la biblioteca, filtrados por capacidad y disponibles
	 *
	 * @param capacidad Capacidad de los recursos para la filtrar los recursos
	 * @return Una lista con los recursos dentro de la base de datos de la biblioteca
	 * @throws PersistenceException si se presenta un error en la base de datos al consultar los recursos
	 */
	@Override
	public List<Recurso> consultarRecursosDisponibles(long capacidad) throws PersistenceException {
		List<Recurso> recursos=null;
		try{
			recursos=recursoMapper.consultarRecursosDisponibles(capacidad);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos disponibles filtrados por capacidad", e);
		}
		return recursos;
	}

	/**
	 * Consulta los recursos dentro de la base de datos de la biblioteca, filtrados por ubicacion y disponibles
	 *
	 * @param ubicacion Ubicacion para la filtrar los recursos
	 * @return Una lista con los recursos dentro de la base de datos de la biblioteca
	 * @throws PersistenceException si se presenta un error en la base de datos al consultar los recursos
	 */
	@Override
	public List<Recurso> consultarRecursosDisponibles(String ubicacion) throws PersistenceException {
		List<Recurso> recursos=null;
		try{
			recursos=recursoMapper.consultarRecursosDisponibles(ubicacion);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al consultar los recursos disponibles filtrador por ubicacion", e);
		}
		return recursos;
	}
}