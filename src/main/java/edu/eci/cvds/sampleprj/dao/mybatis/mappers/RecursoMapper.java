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
 * @version: 5/11/2019
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
     * Retorna una lista con los recursos más usados, de más usado a menos usado que cumplen el tipo(opcional)
     * @return Lista de recursos más usados
     */
    public List<Recurso> consultarRecursosMasUsados(@Param("tipo") TipoRecurso tipo);

    /**
     * Retorna una lista con los recursos menos usados, de más usado a menos usado que cumplen el tipo(opcional)
     * @return Lista de recursos más usados
     */
    public List<Recurso> consultarRecursosMenosUsados(@Param("tipo") TipoRecurso tipo);


    /**
     * Consulta una lista con los recursos más usados, filtrados opcionalmente por el tipo de recurso y la franja horaria
     * @param tipo Tipo de los recursos a consultar
     * @param inicio Inicio de la franja horaria
     * @param fin Fin de la franja horario
     * @return Lista de horarios de mayor ocupación
     */
    public List<Recurso> consultarRecursosMasUsados(@Param("tipo") TipoRecurso tipo, @Param("inicio") Date inicio, @Param("fin") Date fin);
}
