package edu.eci.cvds.samples.entities;

public class Evento {
    int id;
    Horario horaInicio;
    Horario horaFin;
    String estado;

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id=id;
    }

    public Horario getHoraInicio(){
        return horaInicio;
    }

    public void setHoraInicio(Horario horaInicio){
        this.horaInicio=horaInicio;
    }

    public Horario getHoraFin(){
        return horaFin;
    }

    public void setHoraFin(Horario horaFin){
        this.horaFin=horaFin;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }


}
