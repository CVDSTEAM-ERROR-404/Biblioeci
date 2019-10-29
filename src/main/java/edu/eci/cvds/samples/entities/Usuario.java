package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable{
	
	private String area;
	private String nombre;
	private String correo;
	private ArrayList<Reserva> reservas;
}
