package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.UsuarioMapper;
import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;

/**
 * Esta clase conecta los servicios de usuarios por medio de la configuracion de MyBATIS
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class MyBATISUsuarioDAO implements UsuarioDAO {

	@Inject
	private UsuarioMapper usuarioMapper;


	
}