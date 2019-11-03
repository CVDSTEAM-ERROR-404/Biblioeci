package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Recurso;

public interface RecursoDAO{
    public Recurso load(long id) throws PersistenceException;

    public void save(Recurso rec) throws PersistenceException;

    public List<Recurso> consultarRecursos() throws PersistenceException;
	
	public void cambiarEstadoRecurso(int id, String estado) throws PersistenceException;
}