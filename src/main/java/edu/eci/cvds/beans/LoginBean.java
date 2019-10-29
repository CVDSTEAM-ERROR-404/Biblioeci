package edu.eci.cvds.beans;

import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.security.SesionLogger;
import edu.eci.cvds.security.ShiroLogger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="LoginBean")
public class LoginBean {
    private String correo;
    private String password;
    private boolean rememberMe;

    private SesionLogger logger= new ShiroLogger();


    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void login(){
        try {
            logger.login(correo,password,rememberMe);
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/login1.xhtml");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }catch (IOException e) {
            setErrorMessage("Error en el servidor");
        }


    }
    private void setErrorMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public boolean isLogged(){
        return logger.isLogged();
    }

}
