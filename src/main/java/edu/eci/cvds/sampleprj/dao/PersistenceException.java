package edu.eci.cvds.sampleprj.dao;

public class PersistenceException extends Exception{
	public PersistenceException(String message,Exception e){
		super(message,e);
	}

	public PersistenceException(String message){
		super(message);
	}
}