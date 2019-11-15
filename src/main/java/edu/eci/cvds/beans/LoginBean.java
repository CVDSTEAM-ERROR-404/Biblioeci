package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.security.SesionLogger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Esta clase conecta la pagina web del login con el servicio de login de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

@ManagedBean(name="LoginBean")
public class LoginBean extends BasePageBean{

    private String correo;
    private String password;
    private boolean rememberMe;
    @Inject
    private SesionLogger logger;

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
            goHome();
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
    }

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
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goHome(){
        try {
            if (logger.isAdmin()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
            }
        } catch (IOException e) {
            setErrorMessage("Error en el servidor");
        }
    }
}
