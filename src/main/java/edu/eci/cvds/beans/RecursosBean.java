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

/**
 * Esta clase conecta la pagina web con los diferentes servicios de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

@ManagedBean(name="RecursosBean")

@SessionScoped
public class RecursosBean extends BasePageBean {
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

    /**
     * Retorna la ubicacion del recurso que se va a utilizar
     * @return La ubicacion del recurso que se va a utilizar
     */
    public UbicacionRecurso getUbicacionRecurso() { return ubicacionRecurso; }

    /**
     * Cambia la ubicacion del recurso que se va a utilizar
     * @param ubicacionRecurso La ubicacion del recurso que se va a utilizar
     */
    public void setUbicacionRecurso(UbicacionRecurso ubicacionRecurso) { this.ubicacionRecurso = ubicacionRecurso; }

    /**
     * Retorna el estado de la actualizacion
     * @return La cadena de caracteres que significa el estado de la actualizacion
     */
    public String getSuccessUpdate() {
        return successUpdate;
    }

    /**
     * Retorna el estado del recurso que se va a utilizar
     * @return El estado del recurso que se va a utilizar
     */
    public EstadoRecurso getEstadoRecurso() {
        return estadoRecurso;
    }

    /**
     * Cambia el estado del recurso que se va a utilizar
     * @param estadoRecurso El estado del recurso que se va a utilizar
     */
    public void setEstadoRecurso(EstadoRecurso estadoRecurso) {
        this.estadoRecurso = estadoRecurso;
    }

    /**
     * Retorna todos los posibles estados del recurso que se va a utilizar
     * @return Una lista con todos los posibles estados del recurso que se va a utilizar
     */
    public EstadoRecurso[] getEstados(){
        return EstadoRecurso.values();
    }

    /**
     * Retorna todos los posibles tipos del recurso que se va a utilizar
     * @return Una lista con todos los posibles tipos del recurso que se va a utilizar
     */
    public TipoRecurso[] getTipos(){
        return TipoRecurso.values();
    }

    /**
     * Retorna todas las posibles ubicaciones del recurso que se va a utilizar
     * @return Una lista con todas las posibles ubicaciones del recurso que se va a utilizar
     */
    public UbicacionRecurso[] getUbicaciones(){return UbicacionRecurso.values();}

    /**
     * Cambia el tipo del recurso que se va a utilizar
     * @param tipoRecurso El tipo del recurso que se va a utilizar
     */
    public void setTipoRecurso(TipoRecurso tipoRecurso){
        this.tipoRecurso = tipoRecurso;
    }

    /**
     * Retorna el tipo del recurso que se va a utilizar
     * @return El tipo del recurso que se va a utilizar
     */
    public TipoRecurso getTipoRecurso(){
        return tipoRecurso;
    }

    /**
     * Retorna el identificador del recurso utilizado
     * @return El identificador del recurso utilizado
     */
    public int getIdRecurso() { return idRecurso; }

    /**
     * Cambia el identificador del recurso utilizado
     * @param idRecurso El identificador del recurso utilizado
     */
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }

    /**
     * Retorna todas las reservas futuras de un recurso
     * @return Una lista con todas las reservas futuras de un recurso
     */
    public List<Reserva> getReservasFuturas() { return reservasFuturas; }

    /**
     * Cambia las reservas futuras de un recurso
     * @param reservasFuturas Las reservas futuras de un recurso
     */
    public void setReservasFuturas(List<Reserva> reservasFuturas) { this.reservasFuturas = reservasFuturas; }

    /**
     * Retorna todas los recursos disponibles para reservar
     * @return Una lista con todas los recursos disponibles para reservar
     */
	public List<Recurso> getRecursosDisponibles() { return recursosDisponibles; }

    /**
     * Cambia los recursos disponibles para reservar
     * @param recursosDisponibles Una lista con los nuevos recursos disponibles para reservar
     */
    public void setRecursosDisponibles(List<Recurso> recursosDisponibles) { this.recursosDisponibles = recursosDisponibles; }

    /**
     * Determina si se está mostrando el boton
     * @return El valor booleano que determina si se está mostrando el boton
     */
    public boolean isShowButton() { return showButton; }

    /**
     * Cambia la visibilidad del boton
     * @param showButton La nueva visibilidad del boton
     */
    public void setShowButton(boolean showButton) { this.showButton = showButton; }

    /**
     * Registra un recurso en la base de datos de la biblioteca
     * @param nombre El nombre del recurso que se va a registrar
     * @param capacidad La capacidad del recurso que se va a registrar
     */
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

    /**
     * Consulta los recursos dentro de la base de datos de la biblioteca
     * @return Una lista con los recursos dentro de la base de datos de la biblioteca
     */
    public List<Recurso> consultarRecursos(){
        List<Recurso> ans=null;
        try {
            ans = serviciosBiblioEci.consultarRecurso();
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * Consulta los recursos disponibles dentro de la base de datos de la biblioteca
     * @param capacidad El filtro de capacidad de la consulta (si no hay usar 0)
     * @return Una lista con los recursos disponibles dentro de la base de datos de la biblioteca
     */
    public void consultarRecursosDisponibles(int capacidad){
        recursosDisponibles = null;
        try {
            recursosDisponibles = serviciosBiblioEci.consultarRecursosDisponibles(capacidad, ubicacionRecurso, tipoRecurso);
        } catch (ExcepcionServiciosBiblioEci e) {
            setErrorMessage(e.getMessage());
        }

        
    }

    /**
     * Cambia el estado de un recurso de la base de datos
     * @param id El identificador del recurso al que se le va a cambiar el estado
     */
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

    /**
     * Envia un mensaje de operacion exitosa y actualiza la pagina
     * @param message El mensaje de operacion exitosa
     */
	public void successOperation(String message){
        setMessage(message);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancela las reservas pendientes de un recurso de la base de datos
     */
    public void cancelarReservasPendientes(){
        try {
            serviciosBiblioEci.cancelarReservasPendientes(idRecurso);
            successOperation("Actualización exitosa");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            excepcionServiciosBiblioEci.printStackTrace();
        }
    }

    /**
     * Envia un mensaje de error en la pagina web
     * @param message El mensaje que se va a enviar
     */
    protected static void setErrorMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    /**
     * Cambia el mensaje de error en la pagina web
     * @param message El mensaje de error en la pagina web
     */
	protected static void setMessage(String message){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
}