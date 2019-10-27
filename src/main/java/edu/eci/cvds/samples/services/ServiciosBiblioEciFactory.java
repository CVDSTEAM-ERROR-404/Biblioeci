package edu.eci.cvds.samples.services;

import com.google.inject.Injector;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Optional;
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
            //bind(
            }
        }
        );

        testingInjector = createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setClassPathResource("mybatis-config-h2.xml");
                //bind(
            }
        }
        );
    }
    /**
    public X getServiciosBiblioEci(){
        return injector.getInstance(X.class);
    }

    public X getServiciosBiblioEciTesting(){
        return testingInjector.getInstance(X.class);
    }
    **/

    public static ServiciosBiblioEciFactory getInstance(){
        return instance;
    }

}