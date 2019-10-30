package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Reserva implements Serializable{
	
	private int id;
	private String tipo;
	private Date fechaFin;
	private String periodicidad;
	private String estado;
	private ArrayList<EventoProgramado> eventos;
}
