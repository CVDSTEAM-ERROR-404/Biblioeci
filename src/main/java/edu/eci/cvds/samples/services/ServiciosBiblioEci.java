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
 * @version: 2/12/2019
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
     * @param id El identificador del recurso
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
     * @param capacidad Filtra por la capacidad de los recursos (Si es 0 no filtra)
     * @param ubicacionRecurso Filtra por la ubicacion de los recursos (Si es null no filtra)
     * @param tipo  Filtra por el tipo de los recursos (Si es null no filtra)
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
     * @param id El identificador de la reserva
     * @return La reserva de la base de datos de la biblioteca
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
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar un semestre nuevo
     */
    public abstract void registrarSemestre(Date fechaInicio, Date fechaFin) throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna un par de fechas(fecha inicial, fecha final) del semestre
     * @return Fechas del semestre
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar el semestre actual
     */
    public MutablePair<Date, Date> consultarSemestreActual() throws ExcepcionServiciosBiblioEci;

    /**
     * Cancela toda la reserva de un usuario
     * @param reserva La reserva a cancelar
     * @param usuario El usuario de la reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar toda la reserva de un usuario
     */
    public abstract void cancelarReserva(Reserva reserva,Usuario usuario) throws ExcepcionServiciosBiblioEci;

    /**
     * Cancela un evento de la reserva de un usuario
     * @param reserva La reserva a cancelar
     * @param usuario El usuario de la reserva
     * @param evento El evento de la reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar un evento de la reserva de un usuario
     */
    public abstract void cancelarEventoReserva(Reserva reserva, Usuario usuario, Evento evento)throws  ExcepcionServiciosBiblioEci;

    /**
     * Cancela toda la reserva de un usuario
     * @param reserva La reserva a cancelar
     * @param usuario El usuario de la reserva
     * @param fecha La fecha desde la cual se van a cancelar los eventos
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar la reserva de un usuario desde una fecha
     */
    public abstract void cancelarEventosDespues(Reserva reserva, Usuario usuario, Date fecha) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta las reservas de un recurso dado
     * @param idRecurso El identificador del recurso
     * @return Una lista con las reservas de un recurso dado
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas de un recurso dado
     */
    public abstract List<Reserva> consultarReservasRecurso(int idRecurso) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta las reservas activas de un usuario
     * @param usuario El correo del usuario
     * @return Una lista con las reservas activas de un usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas activas de un usuario
     */
    public abstract List<Reserva> consultarReservasActivasUsuario(String usuario) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta las reservas pasadas de un usuario
     * @param usuario El correo del usuario
     * @return Una lista con las reservas pasadas de un usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas pasadas de un usuario
     */
    public abstract List<Reserva> consultarReservasPasadasUsuario(String usuario) throws  ExcepcionServiciosBiblioEci;

    /**
     * Consulta las reservas canceladas de un usuario
     * @param usuario El correo del usuario
     * @return Una lista con las reservas canceladas de un usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas canceladas de un usuario
     */
    public abstract List<Reserva> consultarReservasCanceladasUsuario(String usuario) throws ExcepcionServiciosBiblioEci;

    /**
     * Consulta los eventos activos de una reserva
     * @param reserva El identificador de la reserva
     * @return Una lista con los eventos activos de una reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar los eventos activos de una reserva
     */
    public abstract List<Evento> consultarEventosActivos(int reserva)throws  ExcepcionServiciosBiblioEci;

    /**
     * Retorna una lista con los recursos más usados. Si no se especifica franja horaria ni rango de fechas,
     * se discriminará por la cantidad de reservas activas, de lo contrario por la cantidad de eventos activos
     * que cumplen las especificaciones
     * @param tipo Tipo del  recurso
     * @param franjaHoraria Franja de horas de los eventos
     * @param rangoFechas Rango de fechas de los eventos
     * @param programa Programa de los usuarios solicitantes de los recursos
     * @return Lista de los recursos más usados
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar los recursos mas usados
     */
    public abstract List<MutablePair<Recurso, Long>> consultarRecursosMasUsados( TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna una lista con los recursos menos usados. Si no se especifica franja horaria ni rango de fechas,
     * se discriminará por la cantidad de reservas activas, de lo contrario por la cantidad de eventos activos
     * que cumplen las especificaciones
     * @param tipo Tipo del  recurso
     * @param franjaHoraria Franja de horas de los eventos
     * @param rangoFechas Rango de fechas de los eventos
     * @param programa Programa de los usuarios solicitantes de los recursos
     * @return Lista de los recursos menos usados
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar los recursos menos usados
     */
    public abstract List<MutablePair<Recurso, Long>> consultarRecursosMenosUsados(TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna el rango de horarios de mayor ocupación de los recursos y el número de
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @param tipo Tipo de recurso en los cuales se filtrarán los eventos
     * @param franjaHoraria Horas que serán tenidas en cuenta para el resultado
     * @param rangoFechas Rango de fechas en el cual se filtrarán los eventos
     * @param programa Programa de los usuarios de los cuales se tendrán en cuenta los eventos
     * @return Lista de los horarios de mayor ocupación
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar los horarios de mayor ocupacion
     */
    public abstract List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(TipoRecurso tipo,  MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas,String programa)throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna el rango de horarios de menor ocupación de los recursos y el número de
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @param tipo Tipo de recurso en los cuales se filtrarán los eventos
     * @param franjaHoraria Horas que serán tenidas en cuenta para el resultado
     * @param rangoFechas Rango de fechas en el cual se filtrarán los eventos
     * @param programa Programa de los usuarios de los cuales se tendrán en cuenta los eventos
     * @return Lista de los horarios de menor ocupación
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar los horarios de menor ocupacion
     */
    public abstract List<MutablePair<String, Long>> consultarHorariosMenorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna las reservas recurrentes con los filtros especificados
     * @param tipoReserva Tipo de reserva recurrente
     * @param programa Programa de la universidad
     * @param tipoRecurso Tipo de recursos de las reservas a filtrar
     * @param rangoFechas Rango de de fechas en que la reserva puede estar(maxima y minima fecha), es suficiente con que se crucen
     * @param franja Franja de horarios de los eventos
     * @return Reservas recurrentes con los filtros dados
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar las reservas recurrentes
     */
    public abstract MutablePair<Integer, Integer> consultarReservasRecurrentes(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws ExcepcionServiciosBiblioEci;

    /**
     * Retorna las reservas canceladas con los filtros especificados
     * @param tipoReserva Tipo de reserva
     * @param programa Programa de la universidad
     * @param tipoRecurso Tipo de recursos de las reservas a filtrar
     * @return Reservas canceladas con los filtros dados
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar las reservas canceladas
     */
    public MutablePair<Integer, Integer> consultarReservasCanceladas(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws ExcepcionServiciosBiblioEci ;
}