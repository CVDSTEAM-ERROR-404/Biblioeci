package edu.eci.cvds.beans;

import java.util.List;

import javax.faces.bean.SessionScoped;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.*;
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
	private List<Recurso> recursosDisponibles;
    private String successUpdate = "Actualización exitosa";
    private UbicacionRecurso ubicacionRecurso;


    public UbicacionRecurso getUbicacionRecurso() { return ubicacionRecurso; }

    public void setUbicacionRecurso(UbicacionRecurso ubicacionRecurso) { this.ubicacionRecurso = ubicacionRecurso; }

    public String getSuccessUpdate() {
        return successUpdate;
    }

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

    public UbicacionRecurso[] getUbicaciones(){return UbicacionRecurso.values();}

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
	
	public List<Recurso> getRecursosDisponibles() { return recursosDisponibles; }

    public void setRecursosDisponibles(List<Recurso> recursosDisponibles) { this.recursosDisponibles = recursosDisponibles; }

    public boolean isShowButton() { return showButton; }

    public void setShowButton(boolean showButton) { this.showButton = showButton; }



    public void registrarRecurso(String nombre, int capacidad) {
        try{
			serviciosBiblioEci.registrarRecurso(new Recurso(nombre, ubicacionRecurso, tipoRecurso, capacidad));
			successOperation("Registro exitoso");

        } catch (ExcepcionServiciosBiblioEci e) {
           setErrorMessage(e.getMessage());
        } finally {
            setTipoRecurso(null);
            setUbicacionRecurso(null);
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
	
    public void consultarRecursosDisponibles(int capacidad){
        recursosDisponibles = null;
        try {
            recursosDisponibles = serviciosBiblioEci.consultarRecursosDisponibles(capacidad, ubicacionRecurso, tipoRecurso);
        } catch (ExcepcionServiciosBiblioEci e) {
            setErrorMessage(e.getMessage());
        }

        
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
                successOperation("Actualización exitosa");
            }
        } catch (ExcepcionServiciosBiblioEci e) {
            setErrorMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            setEstadoRecurso(null);
            setUbicacionRecurso(null);
        }
	}
	public void successOperation(String message){
        setMessage(message);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelarReservasPendientes(){
        try {
            serviciosBiblioEci.cancelarReservasPendientes(idRecurso);
            successOperation("Actualización exitosa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            excepcionServiciosBiblioEci.printStackTrace();
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