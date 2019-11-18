package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Evento de reserva utilizado dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */
public class Evento implements Serializable {
    private int id;
    private Date horaInicio;
    private Date horaFin;
    private EstadoReserva estado;

    /**
     * Constructor por defecto de la clase Evento
     */
    public Evento(){};

    /**
     * Constructor de la clase Evento
     * @param horaInicio Fecha de inicio del Evento
     * @param horaFin Fecha de fin del Evento
     */
    public Evento(Date horaInicio, Date horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado= EstadoReserva.Activa;
    }

    /**
     * Muestra el identificador del evento
     * @return El identificador del evento
     */
    public int getId(){
        return id;
    }

    /**
     * Cambia el identificador del evento
     * @param id El nuevo identificador del evento
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Muestra la hora inicial del evento
     * @return La hora inicial del evento
     */
    public Date getHoraInicio(){
        return horaInicio;
    }

    /**
     * Cambia la hora inicial del evento
     * @param horaInicio La nueva hora inicial del evento
     */
    public void setHoraInicio(Date horaInicio){
        this.horaInicio=horaInicio;
    }

    /**
     * Muestra la hora final del evento
     * @return La hora final del evento
     */
    public Date getHoraFin(){
        return horaFin;
    }

    /**
     * Cambia la hora final del evento
     * @param horaFin La nueva hora final del evento
     */
    public void setHoraFin(Date horaFin){
        this.horaFin=horaFin;
    }

    /**
     * Muestra el estado del evento
     * @return El estado del evento
     */
    public EstadoReserva getEstado(){
        return estado;
    }

    /**
     * Cambia el estado del evento
     * @param estado El nuevo estado del evento
     */
    public void setEstado(EstadoReserva estado){
        this.estado = estado;
    }

    /**
     * Muestra La informacion del evento
     * @return Un String que determina la informacion del evento
     */
    @Override
    public String toString() {
        return id+" "+estado+" "+horaInicio+" "+horaFin;
    }

    /**
     * Compara un objeto con el evento para mirar si son iguales
     * @param obj El objeto a comparar
     * @return El valor booleano que determina si el objeto es igual al evento
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal;
        if(!obj.getClass().getSimpleName().equals("Evento")){
            equal = false;
        }
        else {
            Evento evento = (Evento) obj;
            equal = horaInicio.equals(evento.getHoraInicio()) && horaFin.equals(evento.getHoraFin());
        }
        return equal;
    }
}
