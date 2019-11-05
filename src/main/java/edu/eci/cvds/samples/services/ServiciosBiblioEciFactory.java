package edu.eci.cvds.samples.services;

import com.google.inject.Injector;
import edu.eci.cvds.security.SesionLogger;
import edu.eci.cvds.security.ShiroLogger;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import edu.eci.cvds.samples.services.impl.ServiciosBiblioEciImpl;
import edu.eci.cvds.sampleprj.dao.*;
import edu.eci.cvds.sampleprj.dao.mybatis.*;

import static com.google.inject.Guice.createInjector;

public class ServiciosBiblioEciFactory {

    private static ServiciosBiblioEciFactory instance = new ServiciosBiblioEciFactory();

    private static Injector injector;
    private static Injector testingInjector;


    private ServiciosBiblioEciFactory() {

        injector = createInjector(new XMLMyBatisModule() {

            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setClassPathResource("mybatis-config.xml");
                bind(SesionLogger.class).to(ShiroLogger.class);
				bind(ServiciosBiblioEci.class).to(ServiciosBiblioEciImpl.class);
				bind(RecursoDAO.class).to(MyBATISRecursoDAO.class);
				bind(ReservaDAO.class).to(MyBATISReservaDAO.class);
				bind(UsuarioDAO.class).to(MyBATISUsuarioDAO.class);
				bind(HorarioDAO.class).to(MyBATISHorarioDAO.class);

            }
        }
        );

        testingInjector = createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setClassPathResource("mybatis-config-h2.xml");
                bind(SesionLogger.class).to(ShiroLogger.class);
				bind(ServiciosBiblioEci.class).to(ServiciosBiblioEciImpl.class);
				bind(RecursoDAO.class).to(MyBATISRecursoDAO.class);
				bind(ReservaDAO.class).to(MyBATISReservaDAO.class);
				bind(UsuarioDAO.class).to(MyBATISUsuarioDAO.class);
				bind(HorarioDAO.class).to(MyBATISHorarioDAO.class);
            }
        }
        );
    }


    public ServiciosBiblioEci getServiciosBiblioEci(){
        return injector.getInstance(ServiciosBiblioEci.class);
    }

    public ServiciosBiblioEci getServiciosBiblioEciTesting(){
        return testingInjector.getInstance(ServiciosBiblioEci.class);
    }


    public static ServiciosBiblioEciFactory getInstance(){
        return instance;
    }

}