package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Usuario que realiza reservas dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */

public class Usuario implements Serializable{
	
	private String area;
	private String nombre;
	private String correo;
	private ArrayList<Reserva> reservas;

	/**
	 * Muestra el area del usuario
	 * @return El area del usuario
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Cambia el area del usuario
	 * @param area La nueva area del usuario
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * Muestra el nombre del usuario
	 * @return El nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre del usuario
	 * @param nombre El nuevo nombre del usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Muestra el correo del usuario
	 * @return El correo del usuario
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Cambia el correo del usuario
	 * @param correo El nuevo correo del usuario
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Muestra las reservas del usuario
	 * @return Las reservas del usuario
	 */
	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	/**
	 * Cambia las reservas del usuario
	 * @param reservas Las nuevas reservas del usuario
	 */
	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}

	/**
	 * Muestra La informacion del usuario
	 * @return Un String que determina la informacion del usuario
	 */
	@Override
	public String toString() {
		return nombre+" "+correo+" "+area;
	}

	/**
	 * Compara un objeto con el usuario para mirar si son iguales
	 * @param obj El objeto a comparar
	 * @return El valor booleano que determina si el objeto es igual al usuario
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equal;
		if(!obj.getClass().getSimpleName().equals("Usuario")){
			equal = false;
		}
		else {
			Usuario usuario = (Usuario) obj;
			equal = area.equals(usuario.getArea()) && nombre.equals(usuario.getNombre()) && correo.equals(usuario.getCorreo());
		}
		return equal;
	}
}
