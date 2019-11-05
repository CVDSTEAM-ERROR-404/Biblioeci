package edu.eci.cvds.security;

import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;

public interface SesionLogger {
    public void login(String correo,String password,boolean rememberMe) throws ExcepcionServiciosBiblioEci;
    public boolean isLogged();
    public void logout();
}
