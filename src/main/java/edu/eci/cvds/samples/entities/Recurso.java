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
}
