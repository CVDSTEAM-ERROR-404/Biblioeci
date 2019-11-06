package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.HorarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.HorarioMapper;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import com.google.inject.Inject;
import org.mybatis.guice.transactional.Transactional;

/**
 * Esta clase conecta los servicios de horario por medio de la configuracion de MyBATIS
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class MyBATISHorarioDAO implements HorarioDAO{

	@Inject
	private HorarioMapper horarioMapper;
	
}