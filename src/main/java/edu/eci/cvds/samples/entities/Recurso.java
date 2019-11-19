package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 * Recurso utilizado dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class Recurso implements Serializable{
	
	private int id;
	private String nombre;
	private UbicacionRecurso ubicacion;
	private TipoRecurso tipo;
	private int capacidad;
    private EstadoRecurso estado;
    private String inicioDisponibilidad;
    private String finDisponibilidad;


    /**
     * Constructor de la clase Recurso
     * @param nombre El nombre del recurso
     * @param ubicacion La ubicacion del recurso
     * @param tipo El tipo del recurso
     * @param capacidad La capacidad del recurso
     */
    public Recurso(String nombre, UbicacionRecurso ubicacion, TipoRecurso tipo, int capacidad, String inicioDisponibilidad, String finDisponibilidad){
        this.nombre=nombre;
        this.ubicacion=ubicacion;
        this.tipo=tipo;
        this.capacidad=capacidad;
        this.estado=EstadoRecurso.Disponible;
        this.inicioDisponibilidad=inicioDisponibilidad;
        this.finDisponibilidad=finDisponibilidad;
    }

    /**
     * Constructor por defecto de la clase Recurso
     */
    public Recurso(){
    }

    /**
     * Muestra el identificador del recurso
     * @return El identificador del recurso
     */
    public int getId(){
        return id;
    }

    /**
     * Cambia el identificador del recurso
     * @param id El identificador del recurso
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Muestra el nombre del recurso
     * @return El nombre del recurso
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Cambia el nombre del recurso
     * @param nombre El nombre del recurso
     */
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    /**
     * Muestra la ubicacion del recurso
     * @return La ubicacion del recurso
     */
    public UbicacionRecurso getUbicacion(){
        return ubicacion;
    }

    /**
     * Cambia la ubicacion del recurso
     * @param ubicacion La ubicacion del recurso
     */
    public void setUbicacion(UbicacionRecurso ubicacion){
        this.ubicacion=ubicacion;
    }

    /**
     * Muestra el tipo del recurso
     * @return El tipo del recurso
     */
    public TipoRecurso getTipo(){
        return tipo;
    }

    /**
     * Cambia el tipo del recurso
     * @param tipo El tipo del recurso
     */
    public void setTipo(TipoRecurso tipo){
        this.tipo=tipo;
    }

    /**
     * Muestra la capacidad del recurso
     * @return La capacidad del recurso
     */
    public int getCapacidad(){
        return capacidad;
    }

    /**
     * Cambia la capacidad del recurso
     * @param capacidad La capacidad del recurso
     */
    public void setCapacidad(int capacidad){
        this.capacidad=capacidad;
    }

    /**
     * Muestra el estado del recurso
     * @return El estado del recurso
     */
    public EstadoRecurso getEstado(){
        return estado;
    }

    /**
     * Cambia el estado del recurso
     * @param estado El estado del recurso
     */
    public void setEstado(EstadoRecurso estado){
        this.estado=estado;
    }

    /**
     * Muestra la hora en la que inicia la disponibilidad del recurso
     * @return La hora en la que inicia la disponibilidad del recurso
     */
    public String getInicioDisponibilidad() {
        return inicioDisponibilidad;
    }

    /**
     * Cambia la hora en la que inicia la disponibilidad del recurso
     * @param inicioDisponibilidad La nueva hora en la que inicia la disponibilidad del recurso
     */
    public void setInicioDisponibilidad(String inicioDisponibilidad) {
        this.inicioDisponibilidad = inicioDisponibilidad;
    }

    /**
     * Muestra la hora en la que finaliza la disponibilidad del recurso
     * @return La hora en la que finaliza la disponibilidad del recurso
     */
    public String getFinDisponibilidad() {
        return finDisponibilidad;
    }

    /**
     * Cambia la hora en la que finaliza la disponibilidad del recurso
     * @param finDisponibilidad La nueva hora en la que finaliza la disponibilidad del recurso
     */
    public void setFinDisponibilidad(String finDisponibilidad) {
        this.finDisponibilidad = finDisponibilidad;
    }

    /**
     * Muestra el estado completo del recurso en forma de string
     * @return El estado completo del recurso
     */
    @Override
    public String toString() {
        return id+" "+nombre+" "+ubicacion+" "+tipo+" "+capacidad+" "+estado+" "+inicioDisponibilidad+" "+finDisponibilidad;
    }

    /**
     * Compara dos objeto para ver si son similares
     * @param obj El objeto con el que se va a comparar
     * @return El valor booleano que determina si los dos objetos son similares
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal;
        if(!obj.getClass().getSimpleName().equals("Recurso")){
            equal = false;
        }
        else {
            Recurso recurso = (Recurso) obj;
            equal = nombre.equals(recurso.getNombre()) && ubicacion.equals(recurso.getUbicacion()) && capacidad==recurso.getCapacidad() && tipo.equals(recurso.getTipo()) && id==recurso.getId();
        }
        return equal;
    }

    /**
     * Convierte una hora en String de la forma (hh:mm) a formato LocalTime
     * @param tiempo La hora en formato String
     * @return Una hora en formato LocalTime
     */
    private LocalTime fromStringToLocalTime(String tiempo){
        String[] time = tiempo.split(":");
        LocalTime hora = LocalTime.of(Integer.parseInt(time[0]),Integer.parseInt(time[1]));
        return hora;
    }

    /**
     * Muestra si es posible reservar un recurso en una franja horaria
     * @param fechaInicio El inicio de la franja horaria
     * @param fechaFinal El final de la franja horaria
     * @return Un valor booleano que determina si es posible reservar un recurso en una franja horaria
     */
    public boolean isAvailable(Date fechaInicio, Date fechaFinal) {
        LocalTime horaFinal = fromStringToLocalTime(finDisponibilidad);
        LocalTime horaInicial = fromStringToLocalTime(inicioDisponibilidad);
        LocalTime horaInicioReserva = LocalTime.of(fechaInicio.getHours(),fechaFinal.getMinutes());
        LocalTime horaFinReserva = LocalTime.of(fechaFinal.getHours(),fechaFinal.getMinutes());
        boolean ans = true;
        if(horaFinReserva.isAfter(horaFinal) || horaInicioReserva.isBefore(horaInicial)){
            ans = false;
        }
        return ans;
    }
}
