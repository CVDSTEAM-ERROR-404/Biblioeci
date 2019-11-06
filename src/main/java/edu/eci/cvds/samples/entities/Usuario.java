package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Usuario que realiza reservas dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class Usuario implements Serializable{
	
	private String area;
	private String nombre;
	private String correo;
	private ArrayList<Reserva> reservas;
}
