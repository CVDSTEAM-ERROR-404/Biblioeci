package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import edu.eci.cvds.samples.entities.Recurso;

/**
 * Reserva realizada dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class Reserva implements Serializable{
	
	private int id;
	private String tipo;
	private String periodicidad;
	private String estado;
	private ArrayList<Horario> horariosAsignados;
	private Recurso recurso;

	/**
	 * Constructor por defecto de la clase Reserva
	 */
	public Reserva(){
	}

	/**
	 * Muestra el identificador de la reserva
	 * @return El identificador de la reserva
	 */
	public int getID(){
		return id;
	}

	/**
	 * Cambia el identificador de la reserva
	 * @param id El identificador de la reserva
	 */
	public void setID(int id){
		this.id=id;
	}

	/**
	 * Muestra el tipo de la reserva
	 * @return El tipo de la reserva
	 */
	public String getTipo(){
		return tipo;
	}

	/**
	 * Cambia el tipo de la reserva
	 * @param tipo El tipo de la reserva
	 */
	public void setTipo(String tipo){
		this.tipo=tipo;
	}

	/**
	 * Muestra la periodicidad de la reserva
	 * @return La periodicidad de la reserva
	 */
	public String getPeriodicidad(){
		return periodicidad;
	}

	/**
	 * Cambia la periodicidad de la reserva
	 * @param periodicidad La periodicidad de la reserva
	 */
	public void setPeriodicidad(String periodicidad){
		this.periodicidad=periodicidad;
	}

	/**
	 * Muestra el estado de la reserva
	 * @return El estado de la reserva
	 */
	public String getEstado(){
		return estado;
	}

	/**
	 * Cambia el estado de la reserva
	 * @param estado El estado de la reserva
	 */
	public void setEstado(String estado){
		this.estado=estado;
	}

	/**
	 * Muestra los horarios de la reserva
	 * @return Una lista de los horarios de la reserva
	 */
	public ArrayList<Horario> getHorariosAsignados(){
		return horariosAsignados;
	}

	/**
	 * Cambia los horarios de la reserva
	 * @param horariosAsignados Los horarios de la reserva
	 */
	public void setHorariosAsignados(ArrayList<Horario> horariosAsignados){
		this.horariosAsignados=horariosAsignados;
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

}
