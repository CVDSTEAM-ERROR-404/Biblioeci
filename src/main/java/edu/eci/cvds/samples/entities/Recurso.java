package edu.eci.cvds.samples.entities;


import java.io.Serializable;
import java.util.ArrayList;

public class Recurso implements Serializable{
	
	private int id;
	private String nombre;
	private String ubicacion;
	private String tipo;
	private int capacidad;
    private String estado;
	private ArrayList<Reserva> reservas;
    
    public Recurso(String Nombre, String ubicación, String tipo, int capacidad, int id){
        this.nombre=Nombre;
        this.ubicacion=ubicación;
        this.tipo=tipo;
        this.capacidad=capacidad;
        this.id=id;
    }


    public Recurso(){

    }

    
    public int getID(){
        return id;
    }


    public void setID(int id){
        this.id=id;
    }

    

    public String getNombre(){
        return nombre;
    }


    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getUbicacion(){
        return ubicacion;
    }

    public void setUbicacion(String ubicacion){
        this.ubicacion=ubicacion;
    }
    
    
    public String getTipo(){
        return tipo;
    }


    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    
    
    public int getCapacidad(){
        return capacidad;
    }

    public void setCapacidad(int capacidad){
        this.capacidad=capacidad;
    }
    
    
    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado=estado;
    }
    


}
