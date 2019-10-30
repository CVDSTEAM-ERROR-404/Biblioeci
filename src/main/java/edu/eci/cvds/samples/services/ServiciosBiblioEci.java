package edu.eci.cvds.samples.services;

import java.sql.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Recurso;

public interface ServiciosBiblioEci {

    public abstract void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci;


    public abstract Recurso consultarRecusro(long id) throws ExcepcionServiciosBiblioEci;


}