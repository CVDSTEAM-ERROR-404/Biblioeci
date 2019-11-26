package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.security.SesionLogger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Esta clase conecta la pagina web del login con el servicio de login de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */

@ManagedBean(name="LoginBean")
@SessionScoped
public class LoginBean extends BasePageBean{

    @Inject
    private SesionLogger logger;

    private String correo;
    private String password;
    private boolean rememberMe;
    private String url;

	/**
     * Retorna la url de la pagina web
     * @return La url de la pagina web
     */
    public String getUrl() {
        return url;
    }

	/**
     * Cambia la url de la pagina web
     * @param url La nueva url de la pagina web
     */
    public void setUrl(String url) {
        this.url = url;
    }

	/**
     * Muestra si el usuario logueado es un administrador
     * @return El valor booleano que determina si el usuario logueado es un administrador
     */
    public boolean isAdmin() {
        return logger.isAdmin();
    }


    /**
     * Retorna el valor booleano que determina si el usuario se va a recordar
     * @return El valor booleano que determina si el usuario se va a recordar
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * Cambia el valor booleano que determina si el usuario se va a recordar
     * @param rememberMe El nuevo valor booleano que determina si el usuario se va a recordar
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Retorna la clave del usuario logueado
     * @return La clave del usuario logueado
     */
    public String getPassword() {
        return password;
    }

    /**
     * Cambia la clave del usuario logueado
     * @param password La nueva clave del usuario logueado
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna el correo del usuario logueado
     * @return El correo del usuario logueado
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Cambia el correo del usuario logueado
     * @param correo El nuevo correo del usuario logueado
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Loguea al usuario por medio del SessionLogger y actualiza la vista de la pagina web
     */
    public void login(){

        try {
            logger.login(correo,password,rememberMe);
            if(url!=null) {
                String path = url.replace("http://localhost:8080", "");
                FacesContext.getCurrentInstance().getExternalContext().redirect(path);
                url=null;
            }
            else{
                goHome();
            }
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        catch (IOException e) {
            setErrorMessage("Error en el servidor");
        }
    }

    /**
     * Determina si hay un usuario logueado
     * @return El valor booleano que determina si hay un usuario logueado
     */
    public boolean isLogged(){
        return logger.isLogged();
    }

    /**
     * Si hay un usuario logueado, redirige a su respectiva pagina web
     */
    public void avoidAccessToLogin(){
        if( isLogged()){
            goHome();
        }
    }

    /**
     * Desconecta al usuario por medio del SessionLogger y actualiza la vista de la pagina web
     */
    public void logout(){
        logger.logout();
        goHome();
    }

    /**
     * Obtiene la url de la pagina web en la que se esta, previa a la autenticacion
     */
    public void captureUrl(){
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = req.getRequestURL().toString();
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
        }
        catch (IOException e) {
            setErrorMessage("Error en el servidor");
        }
    }
}
