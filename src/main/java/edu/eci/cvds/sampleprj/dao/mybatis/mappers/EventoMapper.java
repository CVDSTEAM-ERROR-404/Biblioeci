package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.EstadoReserva;
import edu.eci.cvds.samples.entities.Evento;
/**
 * Esta interfaz conecta los servicios relacionados con el evento a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
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

    /**
     * Consulta todos los eventos de la base de datos
     * @return Una lista con todos los eventos de la base de datos
     */
    public List<Evento> consultarEventos();

    /**
     * Consulta todos los eventos de una reserva
     * @param id El identificador de la reserva
     * @return Una lista con todos los eventos de una reserva
     */
    public List<Evento> consultarEventos(@Param("reserva") long id);

    public List<Evento> consultarEventosActivos(@Param("reserva") long reserva);

    /**
     * Registra un evento por medio del estado de este
     * @param estado Estado del evento
     * @param fechaInicio Fecha de inicio del evento
     * @param fechaFin Fecha fin del evento
     * @param reserva Reserva del evento

    public void registrarEventoEstado(@Param("estado") String estado, @Param("inicio") Date fechaInicio ,@Param("fin") Date fechaFin, @Param("reserva") Reserva reserva);
*/

    /**
	 * Retorna todos los eventos que interfieran con el horario pasado en los eventos del recurso
	 * @param recurso Id del recurso a consultar
	 * @param fechaInicio Fecha de inicio de la reserva
	 * @param fechaFinal Fecha de fin de la reserva
	 * @return Lista de eventos que interfieran con el horario indicado
	 */
    public List<Evento> consultarEventosRecurso(@Param("recurso") long recurso,@Param("fecha_inicio") Date fechaInicio,@Param("fecha_final") Date fechaFinal);
    
    /**
     * Cambia el estado del evento indicado
     * @param evento Id del evento
     * @param estado Estado a actualizar
     */
    public void cambiarEstadoEvento(@Param("evento") long evento,@Param("estado") EstadoReserva estado);

    /**
     * Cancela todos los eventos de la reserva que inician luego de la fecha dada
     * @param reserva Id de la reserva
     * @param fecha Fecha focal de cancelación
     */
    public void cancelarEventosDespues(@Param("reserva") long reserva, @Param("fecha") Date fecha);

    /**
     * Cancela todos los eventos de la reserva que aún no han ocurrido
     * @param idReserva Id de la reserva
     */
    public void cancelarEventosReserva(@Param("idReserva") long idReserva);
}
