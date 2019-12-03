package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Usuario;

/**
 * Esta interfaz conecta los servicios relacionados con los usuarios a la base de datos
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */

public interface UsuarioMapper {

    /**
     * Consulta un usuario dentro de la base de datos
     * @param correo El correo del usuario
     * @return El usuario dentro de la base de datos, si no existe retorna null
     */
    public Usuario consultarUsuario(@Param("correo") String correo);
}
