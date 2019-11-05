package edu.eci.cvds.security;

import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

public class ShiroLogger implements SesionLogger {
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

    @Override
    public boolean isLogged() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

}
