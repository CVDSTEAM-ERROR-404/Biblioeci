package edu.eci.cvds.samples.services;

import java.sql.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Reserva;

public interface ServiciosBiblioEci {

    public abstract void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci;


    public abstract Recurso consultarRecurso(long id) throws ExcepcionServiciosBiblioEci;

    public abstract List<Recurso> consultarRecurso() throws ExcepcionServiciosBiblioEci;


    public abstract void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws ExcepcionServiciosBiblioEci;

    public abstract List<Reserva> consultarReservasPendientes(int id)throws  ExcepcionServiciosBiblioEci;
    public abstract void cancelarReservasPendientes(int id)throws  ExcepcionServiciosBiblioEci;

}