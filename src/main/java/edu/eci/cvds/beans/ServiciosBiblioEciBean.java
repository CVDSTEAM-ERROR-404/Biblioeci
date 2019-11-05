package edu.eci.cvds.beans;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="ServiciosBean")
public class ServiciosBiblioEciBean extends BasePageBean {
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;
    private TipoRecurso tipoRecurso;
    private EstadoRecurso estadoRecurso;


    public EstadoRecurso getEstadoRecurso() {
        return estadoRecurso;
    }

    public void setEstadoRecurso(EstadoRecurso estadoRecurso) {
        this.estadoRecurso = estadoRecurso;
    }

    public EstadoRecurso[] getEstados(){
        return EstadoRecurso.values();
    }

    public TipoRecurso[] getTipos(){
        return TipoRecurso.values();
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso){
        this.tipoRecurso = tipoRecurso;
    }

    public TipoRecurso getTipoRecurso(){
        return tipoRecurso;
    }




    public void registrarRecurso(String nombre, String ubicacion,  int capacidad) {
        try{
			serviciosBiblioEci.registrarRecurso(new Recurso(nombre, ubicacion, tipoRecurso, capacidad));
			setTipoRecurso(null);
			setMessage("Registro exitoso");
            FacesContext.getCurrentInstance().getExternalContext().redirect("login1.xhtml");

        } catch (ExcepcionServiciosBiblioEci e) {
           setErrorMessage(e.getMessage());
        } catch (IOException e) {
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
	
	
	public void cambiarEstadoRecurso(int id){
		try {
            serviciosBiblioEci.cambiarEstadoRecurso(id , estadoRecurso);
            setEstadoRecurso(null);
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }
	}

    protected static void setErrorMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
	 
	
	protected static void setMessage(String message){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
}