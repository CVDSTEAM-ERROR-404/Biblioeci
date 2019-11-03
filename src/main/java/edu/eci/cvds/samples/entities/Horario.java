package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.Date;

public class Horario implements Serializable {
    private int id;
    private Date fecha ;

    public Horario(Date fecha) {
        this.fecha=fecha;
    }
    public Horario(int id, Date fecha) {
        this(fecha);
        this.id = id;
    }
    public Horario(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
