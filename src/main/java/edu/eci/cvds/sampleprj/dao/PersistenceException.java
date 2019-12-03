package edu.eci.cvds.sampleprj.dao;

/**
 * Esta clase genera las excepciones relacionadas con la capa de persistencia de los servicios de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */

public class PersistenceException extends Exception{

	/**
	 * Constructor de la clase PersistenceException
	 * @param message El mensaje de la excepcion
	 * @param e La excepcion que causo esta nueva excepcion
	 */
	public PersistenceException(String message,Exception e){
		super(message,e);
	}

	/**
	 * Constructor de la clase PersistenceException
	 * @param message El mensaje de la excepcion
	 */
	public PersistenceException(String message){
		super(message);
	}
}