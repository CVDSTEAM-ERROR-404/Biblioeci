package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Horario utilizado dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class Horario implements Serializable {
    private int id;
    private Date fecha ;

    /**
     * Constructor de la clase Horario
     * @param fecha Fecha del horario
     */
    public Horario(Date fecha) {
        this.fecha=fecha;
    }

    /**
     * Constructor de la clase Horario
     * @param fecha Fecha del horario
     * @param id Identificador del horario
     */
    public Horario(int id, Date fecha) {
        this(fecha);
        this.id = id;
    }

    /**
     * Constructor por defecto de la clase Horario
     */
    public Horario(){
    }

    /**
     * Muestra el identificador del horario
     * @return El identificador del horario
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia el identificador del horario
     * @param id El identificador del horario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Muestra la fecha del horario
     * @return La fechar del horario
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Cambia la fecha registrada en el horario
     * @param fecha La fecha registrada en el horario
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
