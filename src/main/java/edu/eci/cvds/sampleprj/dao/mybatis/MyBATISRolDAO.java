package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.RolDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.RolMapper;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;

public class MyBATISRolDAO implements RolDAO{

	@Inject
	private RolMapper rolMapper;
	
}