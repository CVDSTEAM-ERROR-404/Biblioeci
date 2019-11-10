package edu.eci.cvds.samples.entities;

import java.io.Serializable;

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

    /**
     * Constructor de la clase Recurso
     * @param nombre El nombre del recurso
     * @param ubicacion La ubicacion del recurso
     * @param tipo El tipo del recurso
     * @param capacidad La capacidad del recurso
     * @param id El identificador del recurso
     * @param estado El estado del recurso
     */
	public Recurso(String nombre, UbicacionRecurso ubicacion, TipoRecurso tipo, int capacidad, int id, EstadoRecurso estado){
        this(nombre,ubicacion,tipo,capacidad,estado);
        this.id=id;
    }

    /**
     * Constructor de la clase Recurso
     * @param nombre El nombre del recurso
     * @param ubicacion La ubicacion del recurso
     * @param tipo El tipo del recurso
     * @param capacidad La capacidad del recurso
     * @param estado El estado del recurso
     */
    public Recurso(String nombre, UbicacionRecurso ubicacion, TipoRecurso tipo, int capacidad, EstadoRecurso estado){
        this.nombre=nombre;
        this.ubicacion=ubicacion;
        this.tipo=tipo;
        this.capacidad=capacidad;
        this.estado=estado;
    }

    /**
     * Constructor de la clase Recurso
     * @param nombre El nombre del recurso
     * @param ubicacion La ubicacion del recurso
     * @param tipo El tipo del recurso
     * @param capacidad La capacidad del recurso
     */
    public Recurso(String nombre, UbicacionRecurso ubicacion, TipoRecurso tipo, int capacidad){
        this(nombre,ubicacion,tipo,capacidad, EstadoRecurso.Disponible);
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
    public int getID(){
        return id;
    }

    /**
     * Cambia el identificador del recurso
     * @param id El identificador del recurso
     */
    public void setID(int id){
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
     * Muestra el estado completo del recurso en forma de string
     * @return El estado completo del recurso
     */
    public String toString(){
	    return id+" "+nombre+" "+ubicacion+" "+tipo+" "+capacidad+" "+estado;
    }

    /**
     * Compara dos recursos para ver si son similares(el identificador no cuenta)
     * @param recurso El recurso con el que se va a comparar
     * @return El valor booleano que determina si los dos recursos son similares
     */
    public boolean equals(Recurso recurso){
        boolean equal = false;
        if(nombre.equals(recurso.getNombre()) && ubicacion.equals(recurso.getUbicacion()) && capacidad==recurso.getCapacidad() && tipo.equals(recurso.getTipo())){
            equal = true;
        }
        return equal;
    }
}
