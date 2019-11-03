package edu.eci.cvds.samples.services;

import java.sql.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Recurso;

public interface ServiciosBiblioEci {

    public abstract void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci;


    public abstract Recurso consultarRecurso(long id) throws ExcepcionServiciosBiblioEci;

    public abstract List<Recurso> consultarRecurso() throws ExcepcionServiciosBiblioEci;


    public abstract void cambiarEstadoRecurso(int id, String estado) throws ExcepcionServiciosBiblioEci;

}