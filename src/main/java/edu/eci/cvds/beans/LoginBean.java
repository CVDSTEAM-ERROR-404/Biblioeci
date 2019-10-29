package edu.eci.cvds.beans;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="LoginBean")
public class LoginBean {
    private String correo;
    private String contraseña;
    private boolean rememberMe;


    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void login(){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(correo, contraseña,rememberMe);
        try {
            currentUser.login( token );
        } catch ( UnknownAccountException uae ) {
            //username wasn't in the system, show them an error message?
        } catch ( IncorrectCredentialsException ice ) {
            //password didn't match, try again?
        } catch ( LockedAccountException lae ) {
            //account for that username is locked - can't login.  Show them a message?
        }
    catch ( AuthenticationException ae ) {
        //unexpected condition - error?
    }
    }

}
