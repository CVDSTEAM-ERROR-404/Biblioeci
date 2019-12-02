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
 * @version: 18/11/2019
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
     * 
     * @return Una lista con todos los recursos en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar todos
     *                                     los recursos
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
     * Consulta todos los recursos en la base de datos de la biblioteca
     * @return Una lista con todos los recursos en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar todos los recursos
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
     * Consulta los recursos dentro de la base de datos de la biblioteca que estan disponibles
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
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
     * @return Una lista con los eventos no registrados de esa reserva
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
     * @throws ExcepcionServiciosBiblioEci Cuando hay errores en la consulta de la base datos
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
     * @throws ExcepcionServiciosBiblioEci Cuando hay errores en la consulta de la base de datos
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
     * @return Una lista con la reserva de la base de datos de la biblioteca
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
     * Consulta las reservas pendientes de un recurso en la base de datos de la
     * biblioteca
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
     * @return El valor booleano que determina si el recurso esta disponible en lafranja horaria
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
     * Registra las fechas de un semestre
     * @param fechaInicio Fecha inicial del semestre
     * @param fechaFin Fecha final del semestre
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error registrando el semestre
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
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error consultando el semestre
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

    @Override
    public List<MutablePair<Recurso, Long>> consultarRecursosMasUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws ExcepcionServiciosBiblioEci {
        List<MutablePair<Recurso,Long>> usoRecursos;
        try {
            usoRecursos=recursoDAO.consultarRecursosMasUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos mas usados");
        }
        return usoRecursos;
    }

    @Override
    public List<MutablePair<Recurso, Long>> consultarRecursosMenosUsados(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci {
        List<MutablePair<Recurso,Long>> usoRecursos;
        try {
            usoRecursos=recursoDAO.consultarRecursosMenosUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos menos usados");
        }
        return usoRecursos;
    }

    @Override
    public List<MutablePair<String, Long>> consultarHorariosMayorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa) throws ExcepcionServiciosBiblioEci {
        List<MutablePair<String,Long>> topHorarios;
        try {
            topHorarios=recursoDAO.consultarHorariosMayorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los horarios con mayor ocupacion");
        }
        return topHorarios;
    }

    @Override
    public List<MutablePair<String, Long>> consultarHorariosMenorOcupacion(TipoRecurso tipo, MutablePair<Date, Date> franjaHoraria, MutablePair<Date, Date> rangoFechas, String programa)throws ExcepcionServiciosBiblioEci {
        List<MutablePair<String,Long>> topHorarios;
        try {
            topHorarios=recursoDAO.consultarHorariosMenorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los horarios con menor ocupacion");
        }
        return topHorarios;
    }


    private void validacionesCancelacion(Reserva reserva,Usuario usuario)throws  ExcepcionServiciosBiblioEci{
        if(reserva==null){throw new ExcepcionServiciosBiblioEci("La reserva a cancelar no puede ser nula");}
        if(usuario==null){throw new ExcepcionServiciosBiblioEci("El usuario de la reserva no puede ser nulo");}
        if(!usuario.equals(reserva.getUsuario()))throw new ExcepcionServiciosBiblioEci("No se pueden cancelar las reservas de otro usuario");
    }

    
}
