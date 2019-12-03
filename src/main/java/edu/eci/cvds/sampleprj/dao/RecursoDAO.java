package edu.eci.cvds.sampleprj.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;

/**
 * Esta interfaz conecta los servicios de recursos con su base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
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

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     * @param capacidad Filtra por la capacidad de los recursos (Si es 0 no filtra)
     * @param ubicacion Filtra por la ubicacion de los recursos (Si es null no filtra)
     * @param tipo  Filtra por el tipo de los recursos (Si es null no filtra)
     * @throws PersistenceException si se presenta un error en la base de datos al consultar los recursos
     */
    public List<Recurso> consultarRecursosDisponibles(int capacidad, UbicacionRecurso ubicacion, TipoRecurso tipo) throws PersistenceException;


    /**
     * Retorna una lista con los recursos más usados. Si no se especifica franja horaria ni rango de fechas,
     * se discriminará por la cantidad de reservas activas, de lo contrario por la cantidad de eventos activos
     * que cumplen las especificaciones
     * @param tipo Tipo del  recurso
     * @param franjaHoraria Franja de horas de los eventos
     * @param rangoFechas Rango de fechas de los eventos
     * @param programa Programa de los usuarios solicitantes de los recursos
     * @return Lista de los recursos más usados
     */
    public List<MutablePair<Recurso, Long>> consultarRecursosMasUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws PersistenceException;

    
    /**
     * Retorna una lista con los recursos menos usados. Si no se especifica franja horaria ni rango de fechas,
     * se discriminará por la cantidad de reservas activas, de lo contrario por la cantidad de eventos activos
     * que cumplen las especificaciones
     * @param tipo Tipo del  recurso
     * @param franjaHoraria Franja de horas de los eventos
     * @param rangoFechas Rango de fechas de los eventos
     * @param programa Programa de los usuarios solicitantes de los recursos
     * @return Lista de los recursos menos usados
     */
    public List<MutablePair<Recurso, Long>> consultarRecursosMenosUsados(TipoRecurso tipo,MutablePair<Date, Date> franjaHoraria,MutablePair<Date, Date> rangoFechas,String programa) throws PersistenceException;


    /**
     * Retorna el rango de horarios de mayor ocupación de los recursos y el número de 
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @param tipo Tipo de recurso en los cuales se filtrarán los eventos
     * @param franjaHoraria Horas que serán tenidas en cuenta para el resultado
     * @param rangoFechas Rango de fechas en el cual se filtrarán los eventos
     * @param programa Programa de los usuarios de los cuales se tendrán en cuenta los eventos
     * @return Lista de los horarios de mayor ocupación
     */
    public List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria,MutablePair<Date, Date> rangoFechas, String programa) throws PersistenceException;

    /**
     * Retorna el rango de horarios de menor ocupación de los recursos y el número de 
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @param tipo Tipo de recurso en los cuales se filtrarán los eventos
     * @param franjaHoraria Horas que serán tenidas en cuenta para el resultado
     * @param rangoFechas Rango de fechas en el cual se filtrarán los eventos
     * @param programa Programa de los usuarios de los cuales se tendrán en cuenta los eventos
     * @return Lista de los horarios de menor ocupación
     */
    public List<MutablePair<String, Long>> consultarHorariosMenorOcupacion( TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria,MutablePair<Date, Date> rangoFechas, String programa) throws PersistenceException;

}