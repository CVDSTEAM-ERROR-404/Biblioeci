package edu.eci.cvds.samples.services.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.*;

import edu.eci.cvds.samples.entities.*;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
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
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al registrar el recurso, el recurso no posee capacidad valida o el recurso esta vacío
     */
    @Override
    @Transactional
    public void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci {
        if(cli==null){throw new ExcepcionServiciosBiblioEci("El recurso a registrar no puede ser nulo");}
        if(cli.getCapacidad()<=0){throw new ExcepcionServiciosBiblioEci("El recurso "+cli.toString()+"tiene una capacidad invalida");}
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
	public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws ExcepcionServiciosBiblioEci{
		try{
		    Recurso recurso = recursoDAO.load(id);
		    if(estado==null){throw new ExcepcionServiciosBiblioEci("No se puede cambiar a un estado nulo");}
            if(recurso==null){throw new ExcepcionServiciosBiblioEci("No se puede cambiar el estado de un recuso nulo");}
            if(recurso.getEstado().equals(estado)){throw new ExcepcionServiciosBiblioEci("No se puede cambiar el estado de un recuso al que tenia anteriormente");}
		    if(recurso.getEstado().equals(EstadoRecurso.Daño_Total)){throw new ExcepcionServiciosBiblioEci("Un recurso irreparable no puede cambiar su estado");}
		    recursoDAO.cambiarEstadoRecurso(id,estado);
        }catch(PersistenceException e){
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
            throw new ExcepcionServiciosBiblioEci("Error al cancelar las reservas futuras",e);
        }
    }

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca y que están disponibles
     *
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci si se presenta un error en la base de datos al consultar los recursos
     */
    @Override
    public List<Recurso> consultarRecursosDisponibles(int capacidad, UbicacionRecurso ubicacion,TipoRecurso tipo) throws ExcepcionServiciosBiblioEci {
        if(capacidad<0){throw new ExcepcionServiciosBiblioEci("La capacidad no puede ser negativa");}
        List<Recurso> recursos;
        try{
            recursos=recursoDAO.consultarRecursosDisponibles(capacidad,ubicacion,tipo);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos disponibles");
        }
        return  recursos;
    }

    /**
     * Registra una nueva reserva de un recurso y un usuario
     * @param reserva Reserva a registrar
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al realizar una reserva
     */
    @Override
    @Transactional
    public void registrarReserva(Reserva reserva, Date fechaInicio,Date fechaFinRecurrencia,Date fechaFinEvento) throws ExcepcionServiciosBiblioEci {
		validarFechas(fechaInicio,fechaFinRecurrencia,fechaFinEvento);
        try {
            reservaDAO.registrarReserva(reserva);
            if(reserva.getTipo().equals(TipoReserva.Simple)){
                if(!consultarDisponibilidadRecurso(reserva.getRecurso().getId(),fechaInicio,fechaFinEvento)){
                    throw new ExcepcionServiciosBiblioEci("El recurso escogido esta ocupado en ese horario");
                }
                eventoDAO.registrarEvento(new Evento(fechaInicio,fechaFinEvento),reserva.getId());
            }
            else {
                registrarEventosRecurrentes(reserva,fechaInicio,fechaFinRecurrencia,fechaFinEvento);
            }
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al registrar la reserva",e);
        }
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
            throw new ExcepcionServiciosBiblioEci("Error al consultar eventos",e);
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
        if(id<1){throw new ExcepcionServiciosBiblioEci("La reserva con el id "+id+" es invalido");}
        try {
            eventos = eventoDAO.consultarEventos(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar eventos de la reserva"+id);
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
            throw new ExcepcionServiciosBiblioEci("Error al consultar las reservas",e);
        }
        return reservas;
    }

    /**
     * Consulta una reserva de la base de datos
     * @return Una lista con la reserva de la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre algun error al consultar la reserva
     */
    @Override
    public List<Reserva> consultarReserva(long id) throws ExcepcionServiciosBiblioEci {
        if(id<1){throw new ExcepcionServiciosBiblioEci("La reserva con el id "+id+" es invalido");}
        List<Reserva> reservas;
        try {
            reservas = reservaDAO.consultarReserva(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar la reserva"+id,e);
        }
        return reservas;
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
        try{
            usuario = usuarioDAO.consultarUsuario(correo);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosBiblioEci("Error al consultar el usuario con el correo "+correo,e);
        }
        return usuario;
    }


    /**
     * Consulta las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @return Una lista con todas las reservas pendientes de un recurso en la base de datos de la biblioteca
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al consultar las reservas pendientes de un recurso
     */
    @Override
    public List<Reserva> consultarReservasPendientes(int id) throws ExcepcionServiciosBiblioEci {
        if(id<1){throw new ExcepcionServiciosBiblioEci("El id no puede ser menor que 1");}
        List <Reserva> reservas;
        try{
            if(consultarRecurso(id)==null){throw new ExcepcionServiciosBiblioEci("El recurso no existe");}
            reservas=reservaDAO.consultarReservasPendientes(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar las reservas futuras");
        }
        return reservas;
    }

    @Override
    public boolean consultarDisponibilidadRecurso(long recurso, Date fechaInicio, Date fechaFinal) throws ExcepcionServiciosBiblioEci {
        if(fechaInicio==null){throw new ExcepcionServiciosBiblioEci("La fecha inicial no puede ser nula");}
        if(fechaFinal==null){throw new ExcepcionServiciosBiblioEci("La fecha final no puede ser nula");}
        if(recurso<1){throw new ExcepcionServiciosBiblioEci("El id no puede ser menor que 1");}
        boolean ans;
        try{
            if(consultarRecurso(recurso)==null){throw new ExcepcionServiciosBiblioEci("El recurso no existe");}
            List<Evento> eventos = eventoDAO.consultarEventosRecurso(recurso, fechaInicio, fechaFinal);
            ans = eventos.size()==0;
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar la disponibilidad del recurso",e);
        }
        return ans;
    }

    private void registrarEventosRecurrentes(Reserva reserva, Date fechaInicio,Date fechaFinRecurrencia,Date fechaFinEvento)throws ExcepcionServiciosBiblioEci{
        Calendar inicio=Calendar.getInstance();
        Calendar fin= Calendar.getInstance();
        inicio.setTime(fechaInicio);
        fin.setTime(fechaFinEvento);
        int insertados = 0;
        try {
            while(inicio.getTime().before(fechaFinRecurrencia)) {
                if(consultarDisponibilidadRecurso(reserva.getRecurso().getId(),inicio.getTime(),fin.getTime())) {
                    eventoDAO.registrarEvento(new Evento(inicio.getTime(), fin.getTime()), reserva.getId());
                    insertados+=1;
                }
                inicio.add(reserva.getTipo().getCalendarConstant(), 1);
                fin.add(reserva.getTipo().getCalendarConstant(), 1);
            }
            if (insertados==0) throw new ExcepcionServiciosBiblioEci("Todos los horarios de esta reserva estan ocupados actualmente");
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al insertar los eventos recurrentes");
        }
    }
    private void validarFechas(Date fechaInicio,Date fechaFinRecurrencia,Date fechaFinEvento)throws  ExcepcionServiciosBiblioEci{
        if(fechaInicio==null){throw new ExcepcionServiciosBiblioEci("La fecha inicial no puede ser nula");}
        if(fechaFinEvento==null){throw new ExcepcionServiciosBiblioEci("La fecha final no puede ser nula");}
        long duracionEventos=(fechaFinEvento.getTime()-fechaInicio.getTime())/(1000*60);
        if(fechaInicio.before(new Date())){throw new ExcepcionServiciosBiblioEci("La fecha inicial debe ser despues de la fecha actual");}
        if(duracionEventos<=0||(fechaFinRecurrencia!=null && !fechaFinRecurrencia.after(fechaInicio))){
            throw new ExcepcionServiciosBiblioEci("La fecha inicial no puede ser después que la fecha final");
        }
        if(duracionEventos>120){ throw new ExcepcionServiciosBiblioEci("Las reservas máximo pueden durar 2 horas");}
    }
}
