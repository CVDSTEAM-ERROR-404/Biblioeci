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
}
