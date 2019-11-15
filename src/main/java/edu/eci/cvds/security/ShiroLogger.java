package edu.eci.cvds.security;

import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

/**
 * Esta clase conecta el login de un recurso web con la base de datos de la biblioteca por medio de la libreria apache shiro
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class ShiroLogger implements SesionLogger {

    /**
     * Loguea al usuario autenticandolo con la base de datos
     * @param correo El correo del usuario
     * @param password La clave del usuario
     * @param rememberMe El valor booleano que determina si se va a recordar el usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al loguear el usuario (Clave o correo incorrecto)
     */
    @Override
    public void login(String correo,String password,boolean rememberMe) throws ExcepcionServiciosBiblioEci {
        try{
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(correo, new Sha256Hash(password).toHex(),rememberMe);
            currentUser.getSession().setAttribute("Correo",correo);
            currentUser.login( token );
        } catch ( UnknownAccountException uae ) {
            throw new ExcepcionServiciosBiblioEci("El usuario no está registrado",uae);
        } catch ( IncorrectCredentialsException ice ) {
            throw new ExcepcionServiciosBiblioEci("Contraseña incorrecta",ice);
        }
    }

    /**
     * Determina si hay un usuario logueado en el servicio de la biblioteca
     * @return El valor booleano que determina si hay un usuario logueado en el servicio de la biblioteca
     */
    @Override
    public boolean isLogged() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    /**
     * Desconecta al usuario del recurso web de la biblioteca
     */
    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    public boolean isAdmin(){return SecurityUtils.getSubject().hasRole("Administrador");}

    @Override
    public String getUser() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute("Correo");
    }

    public boolean isUser(){return SecurityUtils.getSubject().hasRole("Usuario");}
}
