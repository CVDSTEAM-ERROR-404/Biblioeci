package edu.eci.cvds.sampleprj.dao.mybatis.mappers;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Reserva;


public interface ReservaMapper {
   
	public List<Reserva> consultarReservasPendientes(@Param("id") long id);
}
