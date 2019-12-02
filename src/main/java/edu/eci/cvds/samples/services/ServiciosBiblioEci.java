package edu.eci.cvds.samples.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.tuple.MutablePair;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.UbicacionRecurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.entities.TipoReserva;
import edu.eci.cvds.samples.entities.Evento;
import edu.eci.cvds.samples.entities.Usuario;
import org.mybatis.guice.transactional.Transactional;

/**
 * Esta interfaz describe los servicios utilizados dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
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

    /**
     * Registra una nueva reserva de un recurso y un usuario
     * @param reserva Reserva a registrar
	 * @param fechaInicio Fecha Inicial de la reserva
	 * @param fechaFinRecurrencia Fecha Final de la recurrencia en una reserva recurrente
	 * @param fechaFinEvento Fecha Final de la reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al realizar una reserva
     * @return Una lista con los eventos de esa reserva
     */
    public abstract ArrayList<Evento> registrarReserva(Reserva reserva, Date fechaInicio, Date fechaFinRecurrencia, Date fechaFinEvento) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta todos los eventos
     * @return Una lista con todos los eventos
     * @throws ExcepcionServiciosBiblioEci- Cuando hay errores en la consulta de la base datos
     */
    public abstract List<Evento> consultarEventos() throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta todos los eventos de una reserva
     * @param id El identificador de la reserva
     * @return Una lista con todos los eventos de una reserva
     * @throws ExcepcionServiciosBiblioEci - Cuando hay errores en la consulta de la base datos
     */
    public abstract List<Evento> consultarEvento(int id) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta todas las reservas de la base de datos
     * @return Una lista con todas las reservas de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar las reservas
     */
    public abstract List<Reserva> consultarReservas() throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta una reserva de la base de datos
     * @return Una lista con la reserva de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar la reserva
     */
    public Reserva consultarReserva(long id) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta un usuario dentro de la base de datos
     * @param correo El correo del usuario
     * @return El usuario dentro de la base de datos, si no existe retorna null
     * @throws ExcepcionServiciosBiblioEci Cuando ocrre un error al coonsultar el usuario
     */
    public Usuario consultarUsuario(String correo) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta la disponibilidad de un recurso en cierta franja horaria
     * @param recurso Identificador del recurso
     * @param horaInicio La fecha inicial de la franja
     * @param horaFin La fecha final de la franja
     * @return El valor booleano que determina si el recurso esta disponible en la franja horaria
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar la disponibilidad del recurso
     */
    public abstract boolean consultarDisponibilidadRecurso(long recurso, Date horaInicio, Date horaFin) throws ExcepcionServiciosBiblioEci;

    /**
     * Registra un semestre con las fechas dadas
     * @param fechaInicio Fecha inicial del semestre
     * @param fechaFin Fecha final del semestre
     * @throws ExcepcionServiciosBiblioEci
     */
    public abstract void registrarSemestre(Date fechaInicio, Date fechaFin) throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna un par de fechas(fecha inicial, fecha final) del semestre
     * @return Fechas del semestre
     * @throws ExcepcionServiciosBiblioEci
     */
    public MutablePair<Date, Date> consultarSemestreActual() throws ExcepcionServiciosBiblioEci;

    public abstract void cancelarReserva(Reserva reserva,Usuario usuario) throws ExcepcionServiciosBiblioEci;

    public abstract void cancelarEventoReserva(Reserva reserva, Usuario usuario, Evento evento)throws  ExcepcionServiciosBiblioEci;

    public abstract void cancelarEventosDespues(Reserva reserva, Usuario usuario, Date fecha) throws ExcepcionServiciosBiblioEci;

    public abstract List<Reserva> consultarReservasRecurso(int idRecurso) throws ExcepcionServiciosBiblioEci;

    public abstract List<Reserva> consultarReservasActivasUsuario(String usuario) throws ExcepcionServiciosBiblioEci;

    public abstract List<Reserva> consultarReservasPasadasUsuario(String usuario) throws  ExcepcionServiciosBiblioEci;

    public abstract List<Reserva> consultarReservasCanceladasUsuario(String usuario) throws ExcepcionServiciosBiblioEci;

    public abstract List<Evento> consultarEventosActivos(int reserva)throws  ExcepcionServiciosBiblioEci;

    public abstract List<MutablePair<Recurso, Long>> consultarRecursosMasUsados( TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws ExcepcionServiciosBiblioEci;


    public abstract List<MutablePair<Recurso, Long>> consultarRecursosMenosUsados(TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci;


    public abstract List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas,String programa)throws ExcepcionServiciosBiblioEci;


    public abstract List<MutablePair<String, Long>> consultarHorariosMenorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci;

    public abstract MutablePair<Integer, Integer> consultarReservasRecurrentes(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws ExcepcionServiciosBiblioEci;

    public MutablePair<Integer, Integer> consultarReservasCanceladas(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws ExcepcionServiciosBiblioEci ;
}