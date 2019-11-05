package edu.eci.cvds.samples.services.impl;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.EventoProgramadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.ReservaDAO;

import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;

public class ServiciosBiblioEciImpl implements ServiciosBiblioEci {
    @Inject
    private RecursoDAO recursoDAO;
    @Inject
    private EventoProgramadoDAO eventoProgramadoDAO;
    @Inject
    private ReservaDAO ReservaDAO;

    @Inject
    private UsuarioDAO UsuarioDAO;

    @Override
    public void registrarRecurso(Recurso cli) throws ExcepcionServiciosBiblioEci {
        if(cli.getCapacidad()<=0){throw new ExcepcionServiciosBiblioEci("El recurso "+cli.toString()+"tiene una capacidad invalida");}
        try {
            recursoDAO.save(cli);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al guardar el recurso " + cli.toString(), e);
        }

    }

    @Override
    public Recurso consultarRecurso(long id) throws ExcepcionServiciosBiblioEci {
        Recurso ans = null;
        try {
            ans = recursoDAO.load(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar el recurso " + id, e);
        }
        return ans;
    }

    @Override
    public List<Recurso> consultarRecurso() throws ExcepcionServiciosBiblioEci {
        List<Recurso> ans= null;
        try {
            ans = recursoDAO.consultarRecursos();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosBiblioEci("Error al consultar los recursos", e);
        }
        return ans;
    }
	
	@Override
	public void cambiarEstadoRecurso(int id, EstadoRecurso estado) throws ExcepcionServiciosBiblioEci{
		try{
		    recursoDAO.cambiarEstadoRecurso(id,estado);
        }catch(PersistenceException e){
            throw new ExcepcionServiciosBiblioEci(e.getMessage(), e);
        }
	}
    

}
