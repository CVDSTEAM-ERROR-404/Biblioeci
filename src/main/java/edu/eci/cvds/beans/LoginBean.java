package edu.eci.cvds.beans;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.crypto.hash.Sha256Hash;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="LoginBean")
public class LoginBean {
    private String correo;
    private String password;
    private boolean rememberMe;


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
        try{
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(correo, new Sha256Hash(password).toHex(),rememberMe);
        currentUser.getSession().setAttribute("Correo",correo);
        currentUser.login( token );
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/login1.xhtml");


        } catch ( UnknownAccountException uae ) {
            setErrorMessage("Usuario no registrado");

        } catch ( IncorrectCredentialsException ice ) {
            setErrorMessage("Contraseña incorrecta");
        }
        catch (IOException e) {
            setErrorMessage("Error interno");
        }
    }
    private void setErrorMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public boolean isLogged(){
        return SecurityUtils.getSubject().isAuthenticated();
    }

}
