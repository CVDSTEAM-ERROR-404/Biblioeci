package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;

/**
 * Esta interfaz conecta los servicios de recursos con su base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public interface RecursoDAO{

    /**
     * Carga el recurso a utilizar de la base de datos
     * @param id El identificador del recurso
     * @return El recurso a utilizar
     * @throws PersistenceException Cuando ocurre algun error al cargar el Recurso
     */
    public Recurso load(long id) throws PersistenceException;

    /**
     * Guarda un recurso dentro de la base de datos
     * @param rec El recurso a guardar
     * @throws PersistenceException Cuando ocurre algun error al guardar el Recurso
     */
    public void save(Recurso rec) throws PersistenceException;

    /**
     * Carga todos los recursos de la base de datos
     * @return Una lista con todos los recursos de la base de datos
     * @throws PersistenceException Cuando ocurre algun error al consultar los recursos
     */
    public List<Recurso> consultarRecursos() throws PersistenceException;

    /**
     * Cambia el estado de un recurso dentro de la base de datos
     * @param id El identificador del recurso
     * @param estado El nuevo estado del recurso
     * @throws PersistenceException Cuando ocurre algun error al cambair el estado o cuando no existe el recurso dentro de la base de datos
     */
	public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws PersistenceException;
}