package edu.eci.cvds.samples.services;

import com.google.inject.Injector;
import edu.eci.cvds.security.SesionLogger;
import edu.eci.cvds.security.ShiroLogger;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

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

            }
        }
        );

        testingInjector = createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setClassPathResource("mybatis-config-h2.xml");
                bind(SesionLogger.class).to(ShiroLogger.class);

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