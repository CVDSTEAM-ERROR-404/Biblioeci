package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.security.SesionLogger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;


@ManagedBean(name="LoginBean")

public class LoginBean extends BasePageBean{

    private String correo;
    private String password;
    private boolean rememberMe;
    @Inject
    private SesionLogger logger;

   

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
            FacesContext.getCurrentInstance().getExternalContext().redirect("login1.xhtml");
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            ServiciosBiblioEciBean.setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }catch (IOException e) {
            ServiciosBiblioEciBean.setErrorMessage("Error en el servidor");
        }


    }


    public void isLogged(){
        if( logger.isLogged()){
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect("login1.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
