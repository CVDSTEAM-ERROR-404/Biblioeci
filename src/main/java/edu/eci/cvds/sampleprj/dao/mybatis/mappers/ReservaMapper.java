package edu.eci.cvds.sampleprj.dao.mybatis.mappers;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoReserva;

/**
 * Esta interfaz conecta los servicios relacionados con las reservas a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public interface ReservaMapper {

	/**
	 * Consulta las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 * @return Una lista con las reservas pendientes dentro de la base de datos de la biblioteca
	 */
	public List<Reserva> consultarReservasPendientes(@Param("id") long id);

	/**
	 * Cancela las reservas pendientes de un recurso de la base de datos
	 * @param id El identificador del recurso
	 */
	public void cancelarReservasPendientes(@Param("id")long id);
	
	

	/**
	 * Registra una nueva reserva de un recurso y un usuario
	 * @param reserva Reserva a registrar
	 */
	public void registrarReserva(@Param("reserva")Reserva reserva);

    public List<Reserva> consultarReserva();

	public List<Reserva> consultarReserva(@Param("id")long id);
}
