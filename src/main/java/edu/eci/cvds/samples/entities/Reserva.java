package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import edu.eci.cvds.samples.entities.Recurso;

public class Reserva implements Serializable{
	
	private int id;
	private String tipo;
	private String periodicidad;
	private String estado;
	private ArrayList<Horario> horariosAsignados;
	private Recurso recurso;
	
	public Reserva(){
		
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id=id;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo=tipo;
	}

	public String getPeriodicidad(){
		return periodicidad;
	}
	
	public void setPeriodicidad(String periodicidad){
		this.periodicidad=periodicidad;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public void setEstado(String estado){
		this.estado=estado;
	}
	
	public ArrayList<Horario> getHorariosAsignados(){
		return horariosAsignados;
	}
	
	public void setHorariosAsignados(ArrayList<Horario> horariosAsignados){
		this.horariosAsignados=horariosAsignados;
	}
	
	public Recurso getRecurso(){
		return recurso;
	}
	
	public void setRecurso(Recurso recurso){
		this.recurso = recurso;
	}

}
