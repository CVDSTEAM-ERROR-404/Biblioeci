package edu.eci.cvds.security;

import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;

/**
 * Esta interfaz conecta el login de un recurso web con la base de datos de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */
public interface SesionLogger {

    /**
     * Loguea al usuario autenticandolo con la base de datos
     * @param correo El correo del usuario
     * @param password La clave del usuario
     * @param rememberMe El valor booleano que determina si se va a recordar el usuario
     * @throws ExcepcionServiciosBiblioEci Cuando ocurre un error al loguear el usuario
     */
    public void login(String correo,String password,boolean rememberMe) throws ExcepcionServiciosBiblioEci;

    /**
     * Determina si hay un usuario logueado en el servicio de la biblioteca
     * @return El valor booleano que determina si hay un usuario logueado en el servicio de la biblioteca
     */
    public boolean isLogged();

    /**
     * Desconecta al usuario del recurso web de la biblioteca
     */
    public void logout();

    /**
     * Muestra si el usuario logueado es un cliente de la biblioteca
     * @return El valor booleano que determina si el usuario loguead es un cliente de la biblioteca
     */
    public boolean isUser();

    /**
     * Muestra si el usuario logueado es un administrador de la biblioteca
     * @return El valor booleano que determina si el usuario loguead es un administrador de la biblioteca
     */
    public boolean isAdmin();

    /**
     * Muestra el correo del usuario logueado
     * @return Un String con el correo del usuario logueado
     */
    public String getUser();
}
