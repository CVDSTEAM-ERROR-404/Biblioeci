package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Reserva realizada dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class Reserva implements Serializable{
	
	private int id;
	private TipoReserva tipo;
	private EstadoReserva estado;
	private ArrayList<Evento> eventosAsignados;
	private Recurso recurso;
	private Date fechaSolicitud;
	private Usuario usuario;

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

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date nextEvent(){
		Date nextEvent=null;
		for (Evento evento:eventosAsignados){
			if (nextEvent==null||evento.getHoraInicio().before(nextEvent)){
				nextEvent=evento.getHoraInicio();
			}
		}
		return nextEvent;
	}

	public String toString(){
		return id+" "+tipo+" "+estado+" "+recurso+" "+usuario+" "+fechaSolicitud;
	}

	public boolean equals(Reserva reserva){
		//falta el usuario
		return tipo.equals(reserva.getTipo()) && estado.equals(reserva.getEstado()) && recurso.equals(reserva.getRecurso()) && fechaSolicitud.equals(reserva.getFechaSolicitud());
	}


}
