package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.List;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Recurso;

public interface RecursoMapper {
    public Recurso consultarRecurso(@Param("id") long id);

    public void agregarRecurso(@Param("rec") Recurso rec);

    public List<Recurso> consultarRecursos();
	
	public void cambiarEstado(@Param("id") long id, @Param("estado") EstadoRecurso estado);
}
