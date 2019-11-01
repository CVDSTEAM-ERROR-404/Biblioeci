package edu.eci.cvds.test;


import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.inject.Inject;
import java.sql.Statement;
import java.sql.SQLException;
/*
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
*/
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;

import org.apache.ibatis.session.SqlSession;


import org.junit.Test;

import org.mybatis.guice.transactional.Transactional;
import org.junit.After;


@Transactional
public class ServiciosBiblioEciTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosBiblioEci serviciosBiblioEci;

    public ServiciosBiblioEciTest() {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
    }



    @Test
    public void test(){
		
		//Statement stmt = sqlSession.getConnection().createStatement();
		//stmt.execute("insert into usuario values('prueba@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,'Economia','Pepito Perez')");
		//sqlSession.commit();
    }


    @After
    public void cerrar(){
        //sqlSession.commit();
        //sqlSession.close();
    }

}