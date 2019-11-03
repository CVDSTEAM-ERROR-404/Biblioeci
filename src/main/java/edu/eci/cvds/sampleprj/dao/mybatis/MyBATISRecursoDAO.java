package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.RecursoMapper;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

import java.util.List;

import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;

public class MyBATISRecursoDAO implements RecursoDAO {

	@Inject
	private RecursoMapper recursoMapper;

	
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

	@Transactional
	@Override
	public void save(Recurso rec) throws PersistenceException {
		try {
			recursoMapper.agregarRecurso(rec);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al guardar el recurso " + rec.toString(), e);
		}

	}

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
	
	@Override
	public void cambiarEstadoRecurso(int id, String estado) throws PersistenceException{
		
	}
}