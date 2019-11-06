package edu.eci.cvds.beans;

import java.util.List;

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

/**
 * Esta clase conecta la pagina web con los diferentes servicios de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

@ManagedBean(name="ServiciosBean")
public class ServiciosBiblioEciBean extends BasePageBean {
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;
    private TipoRecurso tipoRecurso;
    private EstadoRecurso estadoRecurso;
    private boolean showButton;
    int idRecurso;
    private List<Reserva> reservasFuturas;

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
     * @return Todos los posibles estados del recurso que se va a utilizar
     */
    public EstadoRecurso[] getEstados(){
        return EstadoRecurso.values();
    }

	/**
     * Retorna todos los posibles tipos del recurso que se va a utilizar
     * @return Todos los posibles tipos del recurso que se va a utilizar
     */
    public TipoRecurso[] getTipos(){
        return TipoRecurso.values();
    }

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
     * @param ubicacion La ubicacion del recurso que se va a registrar
     * @param capacidad La capacidad del recurso que se va a registrar
     */
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
     * Cambia el estado de un recurso de la base de datos
     */
	public void cambiarEstadoRecurso(){
		try {
            serviciosBiblioEci.cambiarEstadoRecurso(idRecurso , estadoRecurso);
            showButton=estadoRecurso.equals(EstadoRecurso.Daño_Reparable)||estadoRecurso.equals(EstadoRecurso.Daño_Total);
            reservasFuturas=serviciosBiblioEci.consultarReservasPendientes(idRecurso);
            if (reservasFuturas!=null){
                //FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
            }
        } catch (ExcepcionServiciosBiblioEci e) {
            setErrorMessage(e.getMessage());
        }
		finally {
            setEstadoRecurso(null);
        }
	}
	
	/**
     * Consulta las reservas pendientes de un recurso de la base de datos
     * @param id El identificador del recurso
     * @return Una lista con las reservas pendientes dentro de la base de datos de la biblioteca
     */
	public List<Reserva> consultarReservasPendientes(int id){
        List<Reserva> reservas=null;
        try {
            reservas=serviciosBiblioEci.consultarReservasPendientes(id);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            excepcionServiciosBiblioEci.printStackTrace();
        }
        return reservas;
    }
	
	/**
     * Cancela las reservas pendientes de un recurso de la base de datos
     * @param id El identificador del recurso
     */
    public void cancelarReservasPendientes(int id){
        try {
            serviciosBiblioEci.cancelarReservasPendientes(id);
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