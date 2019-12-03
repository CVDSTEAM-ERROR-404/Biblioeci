package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Usuario;

/**
 * Esta interfaz conecta los servicios de usuarios con su base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public interface UsuarioDAO{

    /**
     * Consulta un usuario dentro de la base de datos
     * @param correo El correo del usuario
     * @return El usuario dentro de la base de datos, si no existe retorna null
     * @throws PersistenceException Cuando ocrre un error al consultar el usuario
     */
    public Usuario consultarUsuario(String correo) throws PersistenceException;
    
}