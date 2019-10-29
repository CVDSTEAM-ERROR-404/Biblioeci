package edu.eci.cvds.samples.services;

public class ExcepcionServiciosBiblioEci extends Exception{
	private static final long serialVersionUID = 1L;

	public ExcepcionServiciosBiblioEci(String message, Exception e) {
		super(message,e);
	}
	
	public ExcepcionServiciosBiblioEci(String message) {
		super(message);
	}
}