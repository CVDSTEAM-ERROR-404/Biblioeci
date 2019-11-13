package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Reserva;
/**
 * Esta interfaz conecta los servicios relacionados con el evento a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */
public interface EventoMapper {
    /**
     * Cancela todos los eventos pendientes de una reserva específica
     * @param idReserva ID de la reserva
     */
    public void cancelarEventosPendientesReserva(@Param("reserva") long idReserva);

    
}
