package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;


import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Recurso;

/**
 * Esta interfaz conecta los servicios relacionados con los recursos a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public interface RecursoMapper {

    /**
     * Consulta un recurso dentro de la base de datos de la biblioteca
     * @param id El identificador del recurso
     * @return El recurso dentro de la base de datos de la biblioteca, si no existe retorna null
     */
    public Recurso consultarRecurso(@Param("id") long id);

    /**
     * Inserta un recurso dentro de la base de datos de la biblioteca
     * @param rec El recurso que va a ser insertado
     */
    public void agregarRecurso(@Param("rec") Recurso rec);

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     */
    public List<Recurso> consultarRecursos();

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
     * @param capacidad Filtra por la capacidad de los recursos (Si es 0 no filtra)
     * @param ubicacion Filtra por la ubicacion de los recursos (Si es null no filtra)
     * @param tipo  Filtra por el tipo de los recursos (Si es null no filtra)
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     */
    public List<Recurso> consultarRecursosDisponibles(@Param("capacidad") long capacidad,@Param("tipo") TipoRecurso tipo,@Param("ubicacion") UbicacionRecurso ubicacion);

    /**
     * Cambia el estado de un recurso denro de la base de datos y disponibles
     * @param id El identificador del recurso cuyo estado va a ser cambiado
     * @param estado El nuevo estado del recurso
     * @return La cantidad de recursos actualizados
     */
    public int cambiarEstado(@Param("id") long id, @Param("estado") EstadoRecurso estado);
    
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
    public List<MutablePair<Recurso, Long>> consultarRecursosMasUsados(@Param("tipo") TipoRecurso tipo, @Param("franja") MutablePair<Date, Date> franjaHoraria,@Param("rango") MutablePair<Date, Date> rangoFechas,@Param("programa") String programa);

    
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
    public List<MutablePair<Recurso, Long>> consultarRecursosMenosUsados(@Param("tipo") TipoRecurso tipo, @Param("franja") MutablePair<Date, Date> franjaHoraria,@Param("rango") MutablePair<Date, Date> rangoFechas,@Param("programa") String programa);


    /**
     * Retorna el rango de horarios de mayor ocupación de los recursos y el número de 
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @param tipo Tipo de recurso en los cuales se filtrarán los eventos
     * @param franjaHoraria Horas que serán tenidas en cuenta para el resultado
     * @param rangoFechas Rango de fechas en el cual se filtrarán los eventos
     * @param programa Programa de los usuarios de los cuales se tendrán en cuenta los eventos
     * @return Lista de los horarios de mayor ocupación
     */
    public List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(@Param("tipo") TipoRecurso tipo, @Param("franja") MutablePair<Date, Date> franjaHoraria,@Param("rango") MutablePair<Date, Date> rangoFechas,@Param("programa") String programa);

    /**
     * Retorna el rango de horarios de menor ocupación de los recursos y el número de 
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @param tipo Tipo de recurso en los cuales se filtrarán los eventos
     * @param franjaHoraria Horas que serán tenidas en cuenta para el resultado
     * @param rangoFechas Rango de fechas en el cual se filtrarán los eventos
     * @param programa Programa de los usuarios de los cuales se tendrán en cuenta los eventos
     * @return Lista de los horarios de menor ocupación
     */
    public List<MutablePair<String, Long>> consultarHorariosMenorOcupacion(@Param("tipo") TipoRecurso tipo, @Param("franja") MutablePair<Date, Date> franjaHoraria,@Param("rango") MutablePair<Date, Date> rangoFechas,@Param("programa") String programa);

}
