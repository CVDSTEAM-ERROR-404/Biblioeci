package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Reserva realizada dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */
public class Reserva implements Serializable{
	
	private int id;
	private TipoReserva tipo;
	private EstadoReserva estado;
	private ArrayList<Evento> eventosAsignados;
	private Recurso recurso;
	private Date fechaSolicitud;
	private Usuario usuario;

	/**
	 * Constructor de la clase Reserva
	 * @param tipo El tipo de la reserva
	 * @param recurso El recurso sobre el cual se realiza la reserva
	 * @param usuario El usuario que realiza la reserva
	 */
	public Reserva(TipoReserva tipo, Recurso recurso, Usuario usuario) {
		this.tipo = tipo;
		this.recurso = recurso;
		this.fechaSolicitud = new Date();
		this.usuario = usuario;
		this.estado=EstadoReserva.Activa;
	}

	/**
	 * Constructor por defecto de la clase Reserva
	 */
	public Reserva(){
	}

	/**
	 * Muestra el identificador de la reserva
	 * @return El identificador de la reserva
	 */
	public int getId(){
		return id;
	}

	/**
	 * Cambia el identificador de la reserva
	 * @param id El identificador de la reserva
	 */
	public void setId(int id){
		this.id=id;
	}

	/**
	 * Muestra el tipo de la reserva
	 * @return El tipo de la reserva
	 */
	public TipoReserva getTipo(){
		return tipo;
	}

	/**
	 * Cambia el tipo de la reserva
	 * @param tipo El tipo de la reserva
	 */
	public void setTipo(TipoReserva tipo){
		this.tipo=tipo;
	}

	/**
	 * Muestra el estado de la reserva
	 * @return El estado de la reserva
	 */
	public EstadoReserva getEstado(){
		return estado;
	}

	/**
	 * Cambia el estado de la reserva
	 * @param estado El estado de la reserva
	 */
	public void setEstado(EstadoReserva estado){
		this.estado=estado;
	}

	/**
	 * Muestra los eventos de la reserva
	 * @return Una lista de los eventos de la reserva
	 */
	public ArrayList<Evento> getEventosAsignados(){
		return eventosAsignados;
	}

	/**
	 * Cambia los horarios de la reserva
	 * @param eventosAsignados Los horarios de la reserva
	 */
	public void setEventosAsignados(ArrayList<Evento> eventosAsignados){
		this.eventosAsignados=eventosAsignados;
	}


	/**
	 * Muestra el recurso sobre el cual se realizo la reserva
	 * @return El recurso sobre el cual se realizo la reserva
	 */
	public Recurso getRecurso(){
		return recurso;
	}

	/**
	 * Cambia el recurso sobre el cual se realizo la reserva
	 * @param recurso El recurso sobre el cual se realizo la reserva
	 */
	public void setRecurso(Recurso recurso){
		this.recurso = recurso;
	}

	/**
	 * Muestra la fecha cuando se realizo la reserva
	 * @return La fecha cuando se realizo la reserva
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * Cambia la fecha cuando se realizo la reserva
	 * @param fechaSolicitud La nueva fecha cuando se realizo la reserva
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * Muestra el usuario que realizo la reserva
	 * @return El usuario que realizo la reserva
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Cambia el usuario que realizo la reserva
	 * @param usuario El nuevo usuario que realizo la reserva
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Determina la fecha inicial del proximo evento de la reserva cuando es recurrente
	 * @return La fecha incial del proximo evento
	 */
	public Date nextEvent(){
		Date nextEvent=null;
		for (Evento evento:eventosAsignados){
			if (nextEvent==null||evento.getHoraInicio().before(nextEvent)){
				nextEvent=evento.getHoraInicio();
			}
		}
		return nextEvent;
	}

	/**
	 * Muestra La informacion de la reserva
	 * @return Un String que determina la informacion de la reserva
	 */
	@Override
	public String toString() {
		return id+" "+tipo+" "+estado+" "+recurso+" "+usuario+" "+fechaSolicitud;
	}

	/**
	 * Compara un objeto con la reserva para mirar si son iguales
	 * @param obj El objeto a comparar
	 * @return El valor booleano que determina si el objeto es igual a la reserva
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equal;
		if(!obj.getClass().getSimpleName().equals("Reserva")){
			equal = false;
		}
		else {
			Reserva reserva = (Reserva) obj;
			equal = tipo.equals(reserva.getTipo()) && estado.equals(reserva.getEstado()) && recurso.equals(reserva.getRecurso()) && fechaSolicitud.equals(reserva.getFechaSolicitud());
		}
		return equal;
	}


}
