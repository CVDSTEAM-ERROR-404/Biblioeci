package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.ReservaDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ReservaMapper;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;
import edu.eci.cvds.samples.entities.Reserva;
import java.util.List;
public class MyBATISReservaDAO implements ReservaDAO{

	@Inject
	private ReservaMapper reservaMapper;
	
	@Override
	public List<Reserva> consultarReservasPendientes(long id) throws PersistenceException{
		List<Reserva> reservas=null;
		try{
			reservas=reservaMapper.consultarReservasPendientes(id);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al cosultar las reservas futuras ",e);
		}
		return reservas;
	}

	@Override
	public void cancelarReservasPendientes(long id) throws PersistenceException {
		try{
			reservaMapper.cancelarReservasPendientes(id);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al cancelar las reservas futuras ",e);
		}
	}

}