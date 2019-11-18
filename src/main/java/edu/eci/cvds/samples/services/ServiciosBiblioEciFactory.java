package edu.eci.cvds.samples.services;

import com.google.inject.Injector;
import edu.eci.cvds.security.SesionLogger;
import edu.eci.cvds.security.ShiroLogger;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import edu.eci.cvds.samples.services.impl.ServiciosBiblioEciImpl;
import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.ReservaDAO;
import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.EventoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISRecursoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISReservaDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISUsuarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISEventoDAO;
import static com.google.inject.Guice.createInjector;

/**
 * Genera el contexto de los servicios realizados dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 18/11/2019
 */
public class ServiciosBiblioEciFactory {

    private static ServiciosBiblioEciFactory instance = new ServiciosBiblioEciFactory();
    private static Injector injector;
    private static Injector testingInjector;

    /**
     * Constructor de la clase ServiciosBiblioEciFactory
     */
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
                bind(EventoDAO.class).to(MyBATISEventoDAO.class);

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
                bind(EventoDAO.class).to(MyBATISEventoDAO.class);
            }
        }
        );
    }

    /**
     * Muestra la clase que establece los servicios de la biblioteca
     * @return La clase que establece los servicios de la biblioteca
     */
    public ServiciosBiblioEci getServiciosBiblioEci(){
        return injector.getInstance(ServiciosBiblioEci.class);
    }

    /**
     * Muestra la clase que establece los servicios de la biblioteca para realizar pruebas
     * @return La clase que establece los servicios de la biblioteca para realizar pruebas
     */
    public ServiciosBiblioEci getServiciosBiblioEciTesting(){
        return testingInjector.getInstance(ServiciosBiblioEci.class);
    }

    /**
     * Muestra el contexto de los servicios realizados dentro de la biblioteca
     * @return El contexto de los servicios realizados dentro de la biblioteca
     */
    public static ServiciosBiblioEciFactory getInstance(){
        return instance;
    }

}