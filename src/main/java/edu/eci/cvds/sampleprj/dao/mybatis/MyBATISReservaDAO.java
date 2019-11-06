package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.ReservaDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ReservaMapper;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;
import edu.eci.cvds.samples.entities.Reserva;
import java.util.List;

/**
 * Esta clase conecta los servicios de reservas por medio de la configuracion de MyBATIS
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class MyBATISReservaDAO implements ReservaDAO{

	@Inject
	private ReservaMapper reservaMapper;

	/**
	 * Consulta las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @return Una lista con las reservas pendientes dentro de la base de datos de la biblioteca
	 * @throws PersistenceException Cuando ocurre algun error al consultar las reservas pendientes
	 */
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

	/**
	 * Cancela las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @throws PersistenceException Cuando ocurre algun error al cancelar las reservas pendientes
	 */
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