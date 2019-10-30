package edu.eci.cvds.beans;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;


public class ServiciosBiblioEciBean extends BasePageBean {
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

    public void registrarRecurso(String Nombre, String ubicación, String tipo, int capacidad, int id) {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso(Nombre, ubicación, tipo, capacidad, id));
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }

    }

    public void registrarRecurso(Recurso r){
        try {
            serviciosBiblioEci.registrarRecurso(r);
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }
    }

    public List<Recurso> consultarRecursos(){
        List<Recurso> ans=null;
        try {
            ans = serviciosBiblioEci.consultarRecurso();
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }
        return ans;
    }
}