package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.List;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
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
}
