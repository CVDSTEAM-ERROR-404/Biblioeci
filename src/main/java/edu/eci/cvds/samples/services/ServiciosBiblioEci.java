package edu.eci.cvds.samples.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.*;

/**
 * Esta interfaz describe los servicios utilizados dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public interface ServiciosBiblioEci {

    /**
     * Registra un recurso en la base de datos de la biblioteca
     * @param cli El recurso que se va a registrar
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar el recurso
     */
    public abstract void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta un recurso en la base de datos de la biblioteca, si no existe retorna null
     * @param id El identificador del recurso
     * @return El recurso de la base de datos
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar el recurso
     */
    public abstract Recurso consultarRecurso(long id) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta todos los recursos en la base de datos de la biblioteca
     * @return Una lista con todos los recursos en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar todos los recursos
     */
    public abstract List<Recurso> consultarRecurso() throws ExcepcionServiciosBiblioEci;

    /**
     * Cambia el estado de un recurso en la base de datos
     * @param id El identificador del recurso
     * @param estado El nuevo estado del recurso
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cambiar el estado de un recurso
     */
    public abstract void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @return Una lista con todas las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas pendientes de un recurso
     */
    public abstract List<Reserva> consultarReservasPendientes(int id)throws  ExcepcionServiciosBiblioEci;

    /**
     * Cancela las reservas pendientes de un recurso
     * @param id El identificador del recurso
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar las reservas pendientes del recurso
     */
    public abstract void cancelarReservasPendientes(int id)throws  ExcepcionServiciosBiblioEci;


    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci si se presenta un error en la base de datos al consultar los recursos
     */
    public abstract List<Recurso> consultarRecursosDisponibles(int capacidad , UbicacionRecurso ubicacionRecurso,TipoRecurso tipo)throws  ExcepcionServiciosBiblioEci;

    public abstract void registrarReserva(Reserva reserva, Date fechaInicio, Date fechaFinRecurrencia, Date fechaFinEvento) throws ExcepcionServiciosBiblioEci;

    public abstract List<Evento> consultarEventos() throws ExcepcionServiciosBiblioEci;

    public abstract List<Evento> consultarEvento(int id) throws ExcepcionServiciosBiblioEci;

    public abstract List<Reserva> consultarReservas() throws ExcepcionServiciosBiblioEci;

    public List<Reserva> consultarReserva(long id) throws ExcepcionServiciosBiblioEci;

    public Usuario consultarUsuario(String correo) throws ExcepcionServiciosBiblioEci;

}