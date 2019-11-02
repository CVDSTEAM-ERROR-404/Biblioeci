package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.util.Date;

public class Horario implements Serializable {
    private int id;
    private Date fecha ;


    private String hora_inicial;

    public Horario(Date fecha,String hora_inicial) {
        this.fecha=fecha;
        this.hora_inicial=hora_inicial;
    }
    public Horario(int id, Date fecha,String hora_inicial) {
        this(fecha,hora_inicial);
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
    public String getHora_inicial() {
        return hora_inicial;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }


}
