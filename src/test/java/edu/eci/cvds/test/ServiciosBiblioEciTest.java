package edu.eci.cvds.test;


import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.inject.Inject;
/*
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
*/
import org.apache.ibatis.session.SqlSession;


import org.junit.Test;

import org.mybatis.guice.transactional.Transactional;
import org.junit.After;


@Transactional
public class ServiciosBiblioEciTest {

    @Inject
    private SqlSession sqlSession;

    //ServiciosAlquiler serviciosAlquiler;

    /*public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }*/



    @Test
    public void test(){
		assertTrue(true);
    }


    @After
    public void cerrar(){
        //sqlSession.commit();
        //sqlSession.close();
    }

}