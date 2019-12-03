package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.UsuarioMapper;
import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;

/**
 * Esta clase conecta los servicios de usuarios por medio de la configuracion de MyBATIS
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public class MyBATISUsuarioDAO implements UsuarioDAO {

	@Inject
	private UsuarioMapper usuarioMapper;

	/**
	 * Consulta un usuario dentro de la base de datos
	 * @param correo El correo del usuario
	 * @return El usuario dentro de la base de datos, si no existe retorna null
	 * @throws PersistenceException Cuando ocrre un error al consultar el usuario
	 */
	@Override
	public Usuario consultarUsuario(String correo) throws PersistenceException {
		Usuario usuario;
		try{
			usuario = usuarioMapper.consultarUsuario(correo);
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al consultar el usuario con el correo "+correo,e);
		}
		return usuario;
	}
}