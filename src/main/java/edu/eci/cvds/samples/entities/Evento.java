package edu.eci.cvds.samples.entities;

import java.util.Date;

public class Evento {
    private int id;
    private Date horaInicio;
    private Date horaFin;
    private EstadoReserva estado;
    public Evento(){};
    public Evento(Date horaInicio, Date horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado= EstadoReserva.Activa;
    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public Date getHoraInicio(){
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio){
        this.horaInicio=horaInicio;
    }

    public Date getHoraFin(){
        return horaFin;
    }

    public void setHoraFin(Date horaFin){
        this.horaFin=horaFin;
    }

    public EstadoReserva getEstado(){
        return estado;
    }

    public void setEstado(EstadoReserva estado){
        this.estado = estado;
    }

    @Override
    public String toString() {
        return id+" "+estado+" "+horaInicio+" "+horaFin;
    }

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
