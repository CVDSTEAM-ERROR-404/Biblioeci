package edu.eci.cvds.samples.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.ReservaDAO;
import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.EventoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.*;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import org.apache.commons.lang3.tuple.MutablePair;
import org.mybatis.guice.transactional.Transactional;

/**
 * Esta clase implementa los servicios utilizados dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */

public class ServiciosBiblioEciImpl implements ServiciosBiblioEci {
    @Inject
    private RecursoDAO recursoDAO;
    @Inject
    private ReservaDAO reservaDAO;
    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private EventoDAO eventoDAO;

    /**
     * Registra un recurso en la base de datos de la biblioteca
     * @param cli El recurso que se va a registrar
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar el recurso
     */
    @Override
    @Transactional
    public void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci {
        if (cli == null) {throw new ExcepcionServiciosBiblioEci("El recurso a registrar no puede ser nulo"); }
        if (cli.getCapacidad() <= 0) { throw new ExcepcionServiciosBiblioEci("El recurso " + cli.toString() + " tiene una capacidad invalida"); }
        if (cli.getFinDisponibilidad() == null || cli.getInicioDisponibilidad() == null) { throw new ExcepcionServiciosBiblioEci("El recurso " + cli.toString() + " no puede tener algun valor de la franja horaria nulo"); }
        if (!cli.haveValidAvailability()) { throw new ExcepcionServiciosBiblioEci("El recurso " + cli.toString() + " tiene una disponibilidad invalida"); }
        try {
            recursoDAO.save(cli);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al guardar el recurso " + cli.toString(), e);
        }

    }

    /**
     * Consulta un recurso en la base de datos de la biblioteca, si no existe retorna null
     * @param id El identificador del recurso
     * @return El recurso de la base de datos
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar el recurso
     */
    @Override
    public Recurso consultarRecurso(long id) throws ExcepcionServiciosBiblioEci {
        Recurso ans;
        try {
            ans = recursoDAO.load(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar el recurso " + id, e);
        }
        return ans;
    }

    /**
     * Consulta todos los recursos en la base de datos de la biblioteca
     * @return Una lista con todos los recursos en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar todos los recursos
     */
    @Override
    public List<Recurso> consultarRecurso() throws ExcepcionServiciosBiblioEci {
        List<Recurso> ans;
        try {
            ans = recursoDAO.consultarRecursos();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos", e);
        }
        return ans;
    }



    /**
     * Cambia el estado de un recurso en la base de datos
     * @param id El identificador del recurso
     * @param estado El nuevo estado del recurso
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cambiar el estado de un recurso
     */
    @Override
    @Transactional
    public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws ExcepcionServiciosBiblioEci {
        try {
            Recurso recurso = recursoDAO.load(id);
            if (estado == null) { throw new ExcepcionServiciosBiblioEci("No se puede cambiar a un estado nulo"); }
            if (recurso == null) {throw new ExcepcionServiciosBiblioEci("No se puede cambiar el estado de un recuso nulo"); }
            if (recurso.getEstado().equals(estado)) { throw new ExcepcionServiciosBiblioEci("No se puede cambiar el estado de un recuso al que tenia anteriormente"); }
            if (recurso.getEstado().equals(EstadoRecurso.Daño_Total)) { throw new ExcepcionServiciosBiblioEci("Un recurso irreparable no puede cambiar su estado"); }
            recursoDAO.cambiarEstadoRecurso(id, estado);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci(e.getMessage(), e);
        }
    }

    /**
     * Cancela las reservas pendientes de un recurso
     * @param id El identificador del recurso
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar las reservas pendientes del recurso
     */
    @Override
    @Transactional
    public void cancelarReservasPendientes(int id) throws ExcepcionServiciosBiblioEci {
        try {
            eventoDAO.cancelarEventosPendientesRecurso(id);
            reservaDAO.cancelarReservasPendientes(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al cancelar las reservas futuras", e);
        }
    }

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     * @param capacidad Filtra por la capacidad de los recursos (Si es 0 no filtra)
     * @param ubicacion Filtra por la ubicacion de los recursos (Si es null no filtra)
     * @param tipo  Filtra por el tipo de los recursos (Si es null no filtra)
     * @throws ExcepcionServiciosBiblioEci si se presenta un error en la base de datos al consultar los recursos
     */
    @Override
    public List<Recurso> consultarRecursosDisponibles(int capacidad, UbicacionRecurso ubicacion, TipoRecurso tipo) throws ExcepcionServiciosBiblioEci {
        if (capacidad < 0) { throw new ExcepcionServiciosBiblioEci("La capacidad no puede ser negativa"); }
        List<Recurso> recursos;
        try {
            recursos = recursoDAO.consultarRecursosDisponibles(capacidad, ubicacion, tipo);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos disponibles");
        }
        return recursos;
    }

    /**
     * Registra una nueva reserva de un recurso y un usuario
     * @param reserva Reserva a registrar
     * @param fechaInicio Fecha Inicial de la reserva
     * @param fechaFinRecurrencia Fecha Final de la recurrencia en una reserva recurrente
     * @param fechaFinEvento Fecha Final de la reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al realizar una reserva
     * @return Una lista con los eventos de esa reserva
     */
    @Override
    @Transactional
    public ArrayList<Evento> registrarReserva(Reserva reserva, Date fechaInicio, Date fechaFinRecurrencia, Date fechaFinEvento) throws ExcepcionServiciosBiblioEci {
		validarReserva(reserva);
        validarFechas(fechaInicio, fechaFinRecurrencia, fechaFinEvento, reserva);
        ArrayList<Evento> ocupados = null;
        try {
            reservaDAO.registrarReserva(reserva);
            if (reserva.getTipo().equals(TipoReserva.Simple)) {
                if (!consultarDisponibilidadRecurso(reserva.getRecurso().getId(), fechaInicio, fechaFinEvento)) {
                    throw new ExcepcionServiciosBiblioEci("El recurso escogido esta ocupado en ese horario");
                }
                eventoDAO.registrarEvento(new Evento(fechaInicio, fechaFinEvento), reserva.getId());
            } else {
                ocupados=registrarEventosRecurrentes(reserva, fechaInicio, fechaFinRecurrencia, fechaFinEvento);
            }
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al registrar la reserva", e);
        }
        return ocupados;
    }

    /**
     * Consulta todos los eventos
     * @return Una lista con todos los eventos
     * @throws ExcepcionServiciosBiblioEci- Cuando hay errores en la consulta de la base datos
     */
    @Override
    public List<Evento> consultarEventos() throws ExcepcionServiciosBiblioEci {
        List<Evento> eventos;
        try {
            eventos = eventoDAO.consultarEventos();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar eventos", e);
        }
        return eventos;
    }

    /**
     * Consulta todos los eventos de una reserva
     * @param id El identificador de la reserva
     * @return Una lista con todos los eventos de una reserva
     * @throws ExcepcionServiciosBiblioEci - Cuando hay errores en la consulta de la base datos
     */
    @Override
    public List<Evento> consultarEvento(int id) throws ExcepcionServiciosBiblioEci {
        List<Evento> eventos;
        if (id < 1) { throw new ExcepcionServiciosBiblioEci("La reserva con el id " + id + " es invalido"); }
        try {
            eventos = eventoDAO.consultarEventos(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar eventos de la reserva" + id);
        }
        return eventos;
    }

    /**
     * Consulta todas las reservas de la base de datos
     * @return Una lista con todas las reservas de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar las reservas
     */
    @Override
    public List<Reserva> consultarReservas() throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas;
        try {
            reservas = reservaDAO.consultarReserva();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar las reservas", e);
        }
        return reservas;
    }

    /**
     * Consulta una reserva de la base de datos
     * @param id El identificador de la reserva
     * @return La reserva de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar la reserva
     */
    @Override
    public Reserva consultarReserva(long id) throws ExcepcionServiciosBiblioEci {
        if (id < 1) { throw new ExcepcionServiciosBiblioEci("La reserva con el id " + id + " es invalido"); }
        Reserva reserva;
        try {
            reserva = reservaDAO.consultarReserva(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar la reserva" + id, e);
        }
        return reserva;
    }

    /**
     * Consulta un usuario dentro de la base de datos
     * @param correo El correo del usuario
     * @return El usuario dentro de la base de datos, si no existe retorna null
     * @throws ExcepcionServiciosBiblioEci Cuando ocrre un error al coonsultar el usuario
     */
    @Override
    public Usuario consultarUsuario(String correo) throws ExcepcionServiciosBiblioEci {
        Usuario usuario;
        try {
            usuario = usuarioDAO.consultarUsuario(correo);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar el usuario con el correo " + correo, e);
        }
        return usuario;
    }

    /**
     * Consulta las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @param id El identificador del recurso
     * @return Una lista con todas las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas pendientes de un recurso
     */
    @Override
    public List<Reserva> consultarReservasPendientes(int id) throws ExcepcionServiciosBiblioEci {
        if (id < 1) { throw new ExcepcionServiciosBiblioEci("El id no puede ser menor que 1"); }
        List<Reserva> reservas;
        try {
            if (consultarRecurso(id) == null) { throw new ExcepcionServiciosBiblioEci("El recurso no existe"); }
            reservas = reservaDAO.consultarReservasPendientes(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar las reservas futuras");
        }
        return reservas;
    }

    /**
     * Consulta la disponibilidad de un recurso en cierta franja horaria
     * @param recurso Identificador del recurso
     * @param fechaInicio La fecha inicial de la franja
     * @param fechaFinal La fecha final de la franja
     * @return El valor booleano que determina si el recurso esta disponible en la franja horaria
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar la disponibilidad del recurso
     */
    @Override
    public boolean consultarDisponibilidadRecurso(long recurso, Date fechaInicio, Date fechaFinal) throws ExcepcionServiciosBiblioEci {
        if (fechaInicio == null) { throw new ExcepcionServiciosBiblioEci("La fecha inicial no puede ser nula"); }
        if (fechaFinal == null) { throw new ExcepcionServiciosBiblioEci("La fecha final no puede ser nula"); }
        if (recurso < 1) { throw new ExcepcionServiciosBiblioEci("El id no puede ser menor que 1"); }
        boolean ans;
        try {
            Recurso rec = consultarRecurso(recurso);
            if (rec == null) { throw new ExcepcionServiciosBiblioEci("El recurso no existe"); }
            List<Evento> eventos = eventoDAO.consultarEventosRecurso(recurso, fechaInicio, fechaFinal);
            ans = eventos.size() == 0;
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar la disponibilidad del recurso", e);
        }
        return ans;
    }

    /**
     * Registra los distintos eventos de una reserva recurrente
     * @param reserva La reserva que se va a registrar
     * @param fechaInicio  La fecha inicial de la reserva
     * @param fechaFinRecurrencia La fecha final de la recurrencia
     * @param fechaFinEvento La fecha final del evento
	 * @return Una lista con los eventos que no pudieron ser insertados
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar algun evento
     */
    private ArrayList<Evento> registrarEventosRecurrentes(Reserva reserva, Date fechaInicio, Date fechaFinRecurrencia, Date fechaFinEvento) throws ExcepcionServiciosBiblioEci {
        Calendar inicio = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();
        inicio.setTime(fechaInicio);
        fin.setTime(fechaFinEvento);
        ArrayList<Evento> noInsertados = new ArrayList<>();
        int insertados = 0;
        try {
            while (inicio.getTime().before(fechaFinRecurrencia)) {
                if (consultarDisponibilidadRecurso(reserva.getRecurso().getId(), inicio.getTime(), fin.getTime())) {
                    eventoDAO.registrarEvento(new Evento(inicio.getTime(), fin.getTime()), reserva.getId());
                    insertados += 1;
                }
                else {
                    noInsertados.add(new Evento(inicio.getTime(),fin.getTime()));
                }
                inicio.add(reserva.getTipo().getCalendarConstant(), 1);
                fin.add(reserva.getTipo().getCalendarConstant(), 1);
            }
            if (insertados == 0){ throw new ExcepcionServiciosBiblioEci("Todos los horarios de esta reserva estan ocupados actualmente"); }
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al insertar los eventos recurrentes");
        }
        return noInsertados;
    }
	
    /**
     * Verifica que las fechas de una reserva sean validas para registrarla
     * @param fechaInicio La fecha inicial de la reserva
     * @param fechaFinRecurrencia La fecha final de la recurrencia
     * @param fechaFinEvento La fecha final de la reserva
     * @param reserva La reserva que se va a verificar
     * @throws ExcepcionServiciosBiblioEci Cuando las fechas no son validas
     */
    private void validarFechas(Date fechaInicio, Date fechaFinRecurrencia, Date fechaFinEvento, Reserva reserva) throws ExcepcionServiciosBiblioEci {
        if(fechaInicio == null) { throw new ExcepcionServiciosBiblioEci("La fecha inicial no puede ser nula"); }
        if(fechaFinEvento == null) { throw new ExcepcionServiciosBiblioEci("La fecha final no puede ser nula"); }
        long duracionEventos = (fechaFinEvento.getTime() - fechaInicio.getTime()) / (1000 * 60);
        MutablePair<Date,Date> semestre = consultarSemestreActual();
        Date fechaInicioSemestre = semestre.getLeft();
        Date fechaFinSemestre = semestre.getRight();
        if(fechaInicio.before(fechaInicioSemestre) || fechaFinEvento.after(fechaFinSemestre) || (fechaFinRecurrencia!=null && fechaFinRecurrencia.after(fechaFinSemestre))){
            throw new ExcepcionServiciosBiblioEci("La fecha de la reserva no esta en el semestre");
        }
        if(fechaInicio.before(new Date())) { throw new ExcepcionServiciosBiblioEci("La fecha inicial debe ser despues de la fecha actual"); }
        if(duracionEventos <= 0 || (fechaFinRecurrencia != null && !fechaFinRecurrencia.after(fechaInicio))) { throw new ExcepcionServiciosBiblioEci("La fecha inicial no puede ser después que la fecha final"); }
        if(duracionEventos > 120) { throw new ExcepcionServiciosBiblioEci("Las reservas maximo pueden durar 2 horas"); }
        if(!reserva.getRecurso().isAvailable(fechaInicio, fechaFinEvento)) { throw new ExcepcionServiciosBiblioEci("El recurso no se puede reservar a esa hora"); }
    }
	
	/**
     * Verifica que la reserva cumpla con los paramteros basicos
     * @param reserva La reserva que se va a validar
     * @throws ExcepcionServiciosBiblioEci Cuando las fechas no son validas
     */
    private void validarReserva(Reserva reserva) throws ExcepcionServiciosBiblioEci {
        if(reserva == null) { throw new ExcepcionServiciosBiblioEci("No se puede registrar una reserva nula"); }
        if(reserva.getRecurso() == null) { throw new ExcepcionServiciosBiblioEci("No se puede reservar un recurso nulo"); }
        if(!reserva.getRecurso().getEstado().equals(EstadoRecurso.Disponible)){throw new ExcepcionServiciosBiblioEci("No se puede reservar un recurso que no tenga estado disponible"); }
        if(reserva.getUsuario() == null){throw new ExcepcionServiciosBiblioEci("El usuario debe estar autenticado para poder reservar");}
    }

    /**
     * Registra un semestre con las fechas dadas
     * @param fechaInicio Fecha inicial del semestre
     * @param fechaFin Fecha final del semestre
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar un semestre nuevo
     */
    @Override
    public void registrarSemestre(Date fechaInicio, Date fechaFin) throws ExcepcionServiciosBiblioEci {
		if(fechaInicio==null || fechaFin==null){throw new ExcepcionServiciosBiblioEci("No se puede registrar un semestre con alguna fecha nula");}
        if(fechaInicio.after(fechaFin) || fechaInicio.equals(fechaFin)){throw new ExcepcionServiciosBiblioEci("No se puede registrar un semestre con fechas invalidas");}
        try {
            reservaDAO.registrarSemestre(fechaInicio, fechaFin);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al registrar el semestre");
        }
    }

    /**
     * Retorna un par de fechas(fecha inicial, fecha final) del semestre
     * @return Fechas del semestre
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar el semestre actual
     */
    @Override
    public MutablePair<Date, Date> consultarSemestreActual() throws ExcepcionServiciosBiblioEci {
        MutablePair<Date, Date> semestre;
        try {
            semestre = reservaDAO.consultarSemestre();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al registrar el semestre",e);
        }
        return semestre;
    }

    /**
     * Cancela toda la reserva de un usuario
     * @param reserva La reserva a cancelar
     * @param usuario El usuario de la reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar toda la reserva de un usuario
     */
    @Override
    @Transactional
    public void cancelarReserva(Reserva reserva,Usuario usuario) throws ExcepcionServiciosBiblioEci {
        try {
            validacionesCancelacion(reserva,usuario);
            if(reserva.getFechaFin().before(new Date()))throw new ExcepcionServiciosBiblioEci("No se pueden cancelar reservas que ya finalizaron");
            if(reservaDAO.reservaEnCurso(reserva.getId())!=null)throw new ExcepcionServiciosBiblioEci("La reserva esta en curso");
            reservaDAO.cambiarEstadoReserva(reserva.getId(), EstadoReserva.Cancelada);
            eventoDAO.cancelarEventosReserva(reserva.getId());
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al cancelar la reserva",e);
        }
    }

    /**
     * Cancela un evento de la reserva de un usuario
     * @param reserva La reserva a cancelar
     * @param usuario El usuario de la reserva
     * @param evento El evento de la reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar un evento de la reserva de un usuario
     */
    @Override
    @Transactional
    public void cancelarEventoReserva(Reserva reserva, Usuario usuario, Evento evento) throws ExcepcionServiciosBiblioEci {
        try{
            if(evento==null){throw new ExcepcionServiciosBiblioEci("El evento a cancelar no puede ser nulo");}
            validacionesCancelacion(reserva,usuario);
            Date today = new Date();
            if(evento.getHoraFin().after(today)&&evento.getHoraInicio().before(today))throw new ExcepcionServiciosBiblioEci("El evento esta en curso");
            if(evento.getHoraFin().before(today))throw new ExcepcionServiciosBiblioEci("El evento ya finalizo");
            eventoDAO.cambiarEstadoEvento(evento.getId(),EstadoReserva.Cancelada);
            if (eventoDAO.consultarEventosActivos(reserva.getId()).size()==0){
                reservaDAO.cambiarEstadoReserva(reserva.getId(),EstadoReserva.Cancelada);
            }
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al cancelar el evento",e);
        }
    }

    /**
     * Cancela toda la reserva de un usuario
     * @param reserva La reserva a cancelar
     * @param usuario El usuario de la reserva
     * @param fecha La fecha desde la cual se van a cancelar los eventos
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al cancelar la reserva de un usuario desde una fecha
     */
    @Override
    @Transactional
    public void cancelarEventosDespues(Reserva reserva, Usuario usuario, Date fecha) throws ExcepcionServiciosBiblioEci {
        try{
            validacionesCancelacion(reserva,usuario);
            Date today =new Date();
            if(fecha==null){throw new ExcepcionServiciosBiblioEci("La fecha para cancelar eventos no puede ser nula");}
            if(fecha.before(today))throw new ExcepcionServiciosBiblioEci("No se pueden cancelar eventos pasados");
            if(reserva.getFechaFin().before(fecha))throw new ExcepcionServiciosBiblioEci("La reserva ya finalizo");
            eventoDAO.cancelarEventosDespues(reserva.getId(),fecha);
            if (eventoDAO.consultarEventosActivos(reserva.getId()).size()==0){
                reservaDAO.cambiarEstadoReserva(reserva.getId(),EstadoReserva.Cancelada);
            }
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al cancelar el evento",e);
        }
    }

    /**
     * Consulta las reservas de un recurso dado
     * @param idRecurso El identificador del recurso
     * @return Una lista con las reservas de un recurso dado
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas de un recurso dado
     */
    @Override
    public List<Reserva> consultarReservasRecurso(int idRecurso) throws ExcepcionServiciosBiblioEci {
        List<Reserva> reservas;
        if(idRecurso<1){throw new ExcepcionServiciosBiblioEci("Identificador de recurso invalido");}
        try {
            reservas=reservaDAO.consultarReservasRecurso(idRecurso);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error consultar las reservas del recurso",e);
        }
        return reservas;
    }

    /**
     * Consulta las reservas activas de un usuario
     * @param usuario El correo del usuario
     * @return Una lista con las reservas activas de un usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas activas de un usuario
     */
    @Override
    public List<Reserva> consultarReservasActivasUsuario(String usuario) throws ExcepcionServiciosBiblioEci {
        if(usuario==null){throw new ExcepcionServiciosBiblioEci("No se pueden consultar reservas de un usuario nulo");}
        List<Reserva> reservas;
        try {
            reservas=reservaDAO.consultarReservasActivasUsuario(usuario);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error consultar las reservas del usuario",e);
        }
        return  reservas;
    }

    /**
     * Consulta las reservas pasadas de un usuario
     * @param usuario El correo del usuario
     * @return Una lista con las reservas pasadas de un usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas pasadas de un usuario
     */
    @Override
    public List<Reserva> consultarReservasPasadasUsuario(String usuario) throws ExcepcionServiciosBiblioEci {
        if(usuario==null){throw new ExcepcionServiciosBiblioEci("No se pueden consultar reservas de un usuario nulo");}
        List<Reserva> reservas;
        try {
            reservas=reservaDAO.consultarReservasPasadasUsuario(usuario);
        }catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error consultar las reservas del usuario",e);
        }
        return  reservas;

    }

    /**
     * Consulta las reservas canceladas de un usuario
     * @param usuario El correo del usuario
     * @return Una lista con las reservas canceladas de un usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas canceladas de un usuario
     */
    @Override
    public List<Reserva> consultarReservasCanceladasUsuario(String usuario) throws ExcepcionServiciosBiblioEci {
        if(usuario==null){throw new ExcepcionServiciosBiblioEci("No se pueden consultar reservas de un usuario nulo");}
        List<Reserva> reservas;
        try {
            reservas=reservaDAO.consultarReservasCanceladasUsuario(usuario);
        }catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error consultar las reservas del usuario",e);
        }
        return  reservas;
    }

    /**
     * Consulta los eventos activos de una reserva
     * @param reserva El identificador de la reserva
     * @return Una lista con los eventos activos de una reserva
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar los eventos activos de una reserva
     */
    @Override
    public List<Evento> consultarEventosActivos(int reserva) throws ExcepcionServiciosBiblioEci {
        if(reserva<1){throw new ExcepcionServiciosBiblioEci("Identificador de reserva invalido");}
        List<Evento> eventos;
        try {
            eventos=eventoDAO.consultarEventosActivos(reserva);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los eventos activos de la reserva");
        }
        return  eventos;
    }

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
    @Override
    public List<MutablePair<Recurso, Long>> consultarRecursosMasUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws ExcepcionServiciosBiblioEci {
        validarRangos(franjaHoraria, rangoFechas);
        List<MutablePair<Recurso,Long>> usoRecursos;
        try {
            usoRecursos=recursoDAO.consultarRecursosMasUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos mas usados");
        }
        return usoRecursos;
    }

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
    @Override
    public List<MutablePair<Recurso, Long>> consultarRecursosMenosUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci {
        validarRangos(franjaHoraria, rangoFechas);
        List<MutablePair<Recurso, Long>> usoRecursos;
        try {
            usoRecursos=recursoDAO.consultarRecursosMenosUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos menos usados");
        }
        return usoRecursos;
    }

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
    @Override
    public List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws ExcepcionServiciosBiblioEci {
        validarRangos(franjaHoraria, rangoFechas);
        List<MutablePair<String,Long>> topHorarios;
        try {
            topHorarios=recursoDAO.consultarHorariosMayorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los horarios con mayor ocupacion");
        }
        return topHorarios;
    }

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
    @Override
    public List<MutablePair<String, Long>> consultarHorariosMenorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci {
        validarRangos(franjaHoraria, rangoFechas);
        List<MutablePair<String,Long>> topHorarios;
        try {
            topHorarios=recursoDAO.consultarHorariosMenorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los horarios con menor ocupacion");
        }
        return topHorarios;
    }

    private void validarRangos(MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas)throws  ExcepcionServiciosBiblioEci{
        if(franjaHoraria!=null && franjaHoraria.getLeft().after(franjaHoraria.getRight())){throw new ExcepcionServiciosBiblioEci("Franja Horaria Invalida");}
        if(rangoFechas!=null && rangoFechas.getLeft().after(rangoFechas.getRight())){throw new ExcepcionServiciosBiblioEci("Rango de Fechas Invalida");}
    }
    private void validacionesCancelacion(Reserva reserva,Usuario usuario)throws  ExcepcionServiciosBiblioEci{
        if(reserva==null){throw new ExcepcionServiciosBiblioEci("La reserva a cancelar no puede ser nula");}
        if(usuario==null){throw new ExcepcionServiciosBiblioEci("El usuario de la reserva no puede ser nulo");}
        if(!usuario.equals(reserva.getUsuario()))throw new ExcepcionServiciosBiblioEci("No se pueden cancelar las reservas de otro usuario");
    }

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
    @Override
    public MutablePair<Integer, Integer> consultarReservasRecurrentes(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws ExcepcionServiciosBiblioEci {
        validarRangos(franja, rangoFechas);
        MutablePair<Integer, Integer> reservas = new MutablePair<Integer, Integer>();
        try {
            reservas.setLeft(reservaDAO.consultarReservasRecurrentes(tipoReserva, programa, tipoRecurso, rangoFechas, franja).size());
            reservas.setRight(reservaDAO.consultarReservasSimples(programa, tipoRecurso, rangoFechas, franja).size());
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar datos de las reservas recurrentes y simples");
        }
        return reservas;
    }

    /**
     * Retorna las reservas canceladas con los filtros especificados
     * @param tipoReserva Tipo de reserva
     * @param programa Programa de la universidad
     * @param tipoRecurso Tipo de recursos de las reservas a filtrar
     * @return Reservas canceladas con los filtros dados
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar las reservas canceladas
     */
    @Override
    public MutablePair<Integer, Integer> consultarReservasCanceladas(TipoReserva tipoReserva, String programa, TipoRecurso tipoRecurso, MutablePair<Date, Date> rangoFechas, MutablePair<Date, Date> franja) throws ExcepcionServiciosBiblioEci {
        validarRangos(franja, rangoFechas);
        MutablePair<Integer, Integer> reservas = new MutablePair<Integer, Integer>();
        try {
            reservas.setLeft(reservaDAO.consultarReservasCanceladas(tipoReserva, programa, tipoRecurso).size());
            reservas.setRight(reservaDAO.consultarReservasActivas(tipoReserva, programa, tipoRecurso).size());
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar datos de las reservas recurrentes y simples");
        }
        return reservas;
    }
}
