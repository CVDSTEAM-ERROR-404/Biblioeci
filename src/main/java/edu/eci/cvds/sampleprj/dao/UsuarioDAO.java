package edu.eci.cvds.sampleprj.dao;


import edu.eci.cvds.samples.entities.Usuario;

/**
 * Esta interfaz conecta los servicios de usuarios con su base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public interface UsuarioDAO{

    public Usuario consultarUsuario(String correo) throws PersistenceException;
    
}