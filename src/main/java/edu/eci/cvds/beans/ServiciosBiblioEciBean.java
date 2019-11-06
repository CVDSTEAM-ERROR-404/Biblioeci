package edu.eci.cvds.beans;

import java.util.List;

import javax.faces.bean.SessionScoped;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.EstadoRecurso;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.Reserva;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="ServiciosBean")

@SessionScoped
public class ServiciosBiblioEciBean extends BasePageBean {
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;
    private TipoRecurso tipoRecurso;
    private EstadoRecurso estadoRecurso;
    private boolean showButton;
    private int idRecurso;
	
    private List<Reserva> reservasFuturas;


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

    public int getIdRecurso() { return idRecurso; }

    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }

    public List<Reserva> getReservasFuturas() { return reservasFuturas; }

    public void setReservasFuturas(List<Reserva> reservasFuturas) { this.reservasFuturas = reservasFuturas; }

    public boolean isShowButton() { return showButton; }

    public void setShowButton(boolean showButton) { this.showButton = showButton; }



    public void registrarRecurso(String nombre, String ubicacion,  int capacidad) {
        try{
			serviciosBiblioEci.registrarRecurso(new Recurso(nombre, ubicacion, tipoRecurso, capacidad));
			setMessage("Registro exitoso");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");

        } catch (ExcepcionServiciosBiblioEci e) {
           setErrorMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            setTipoRecurso(null);
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
		    idRecurso=id;
            serviciosBiblioEci.cambiarEstadoRecurso(idRecurso , estadoRecurso);
            showButton=estadoRecurso.equals(EstadoRecurso.Daño_Reparable);
            reservasFuturas=serviciosBiblioEci.consultarReservasPendientes(idRecurso);
            if (reservasFuturas.size()!=0 && !(estadoRecurso.equals(EstadoRecurso.Disponible))) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/cancel_reserva.xhtml");
                }
            else {
                setMessage("Actualización exitosa");
                FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
            }
        } catch (ExcepcionServiciosBiblioEci e) {
            setErrorMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            setEstadoRecurso(null);
        }
	}

    public void cancelarReservasPendientes(){
        try {
            serviciosBiblioEci.cancelarReservasPendientes(idRecurso);
            setMessage("Actualización exitosa");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            excepcionServiciosBiblioEci.printStackTrace();
        } catch (IOException e) {
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