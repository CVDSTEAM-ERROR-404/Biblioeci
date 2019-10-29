package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.EventoProgramadoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.EventoProgramadoMapper;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;

public class MyBATISEventoProgramadoDAO implements EventoProgramadoDAO{

	@Inject
	private EventoProgramadoMapper eventoProgramadoMapper;
	
}