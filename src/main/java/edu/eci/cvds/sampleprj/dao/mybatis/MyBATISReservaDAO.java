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

	/**
	 * Registra una nueva reserva de un recurso y un usuario
	 *
	 * @param reserva Reserva a registrar
	 */
	@Override
	public void registrarReserva(Reserva reserva) throws PersistenceException {
		try{
			reservaMapper.registrarReserva(reserva);
		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println(e.getMessage());
			throw new PersistenceException("Error al registrar la reserva",e);
		}
	}


	@Override
	public List<Reserva> consultarReserva(long id) throws PersistenceException {
		List<Reserva> reservas=null;
		try{
			reservas=reservaMapper.consultarReserva(id);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al cosultar la reserva "+id,e);
		}
		return reservas;
	}

	@Override
	public List<Reserva> consultarReserva() throws PersistenceException {
		List<Reserva> reservas=null;
		try{
			reservas=reservaMapper.consultarReserva();
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al cosultar las reservas",e);
		}
		return reservas;
	}
}