package edu.eci.cvds.beans;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name="SeriviosBean")
public class ServiciosBiblioEciBean extends BasePageBean {
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;



    public void registrarRecurso(String nombre, String ubicacion, String tipo, int capacidad) {
        try{
			serviciosBiblioEci.registrarRecurso(new Recurso(nombre, ubicacion, tipo, capacidad, "OK"));

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
	
	
	public void cambiarEstadoRecurso(int id, String estado){
		try {
            serviciosBiblioEci.cambiarEstadoRecurso(id , estado);
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }
	}
}