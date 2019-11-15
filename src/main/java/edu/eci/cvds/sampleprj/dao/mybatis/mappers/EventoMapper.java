package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.Reserva;
/**
 * Esta interfaz conecta los servicios relacionados con el evento a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */
public interface EventoMapper {
    /**
     * Cancela todos los eventos pendientes de una recurso específico
     * @param idRecurso ID del recurso
     */
    public void cancelarEventosPendientesRecurso(@Param("recurso") long idRecurso);

    /**
     * Registra un nuevo evento de una reserva
     * @param evento Evento a registrar
     * @param idReserva id de la Reserva del evento
     */
    public void registrarEvento(@Param("evento") Evento evento,  @Param("idReserva") long idReserva);

    public List<Evento> consultarEventos();

    public List<Evento> consultarEventos(@Param("reserva") long id);

    /**
     * Registra un evento por medio del estado de este
     * @param estado Estado del evento
     * @param fechaInicio Fecha de inicio del evento
     * @param fechaFin Fecha fin del evento
     * @param reserva Reserva del evento

    public void registrarEventoEstado(@Param("estado") String estado, @Param("inicio") Date fechaInicio ,@Param("fin") Date fechaFin, @Param("reserva") Reserva reserva);
*/
    
}
