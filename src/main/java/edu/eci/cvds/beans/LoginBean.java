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
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            RecursosBean.setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }catch (IOException e) {
            RecursosBean.setErrorMessage("Error en el servidor");
        }


    }

    /**
     * Si hay un usuario logueado, redirige a su respectiva pagina web
     */
    public void isLogged(){
        if( logger.isLogged()){
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Desconecta al usuario por medio del SessionLogger y actualiza la vista de la pagina web
     */
    public void logout(){
        logger.logout();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
