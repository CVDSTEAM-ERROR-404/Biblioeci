package edu.eci.cvds.samples.services;

/**
 * Esta clase genera las excepciones relacionadas con la capa de aplicacion de los servicios de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class ExcepcionServiciosBiblioEci extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase ExcepcionServiciosBiblioEci
	 * @param message El mensaje de la excepcion
	 * @param e La excepcion que causo esta nueva excepcion
	 */
	public ExcepcionServiciosBiblioEci(String message, Exception e) {
		super(message,e);
	}

	/**
	 * Constructor de la clase ExcepcionServiciosBiblioEci
	 * @param message El mensaje de la excepcion
	 */
	public ExcepcionServiciosBiblioEci(String message) {
		super(message);
	}
}