package edu.eci.cvds.guice;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import edu.eci.cvds.security.SesionLogger;
import edu.eci.cvds.security.ShiroLogger;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import edu.eci.cvds.sampleprj.dao.HorarioDAO;
import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.ReservaDAO;

import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISHorarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISRecursoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISReservaDAO;

import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISUsuarioDAO;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.impl.ServiciosBiblioEciImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Esta clase conecta la pagina web con el contexto de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */

public class GuiceContextListener implements ServletContextListener {

    /**
     * Elimina el contexto del servicio de la biblioteca
     * @param servletContextEvent El evento que elimina el contexto del servicio de la biblioteca
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(Injector.class.getName());
    }

    /**
     * Genera el contexto del servicio de la biblioteca
     * @param servletContextEvent El evento que genera el contexto del servicio de la biblioteca
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Injector injector = Guice.createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setEnvironmentId("development");
                setClassPathResource("mybatis-config.xml");

                bind(ServiciosBiblioEci.class).to(ServiciosBiblioEciImpl.class);
                bind(RecursoDAO.class).to(MyBATISRecursoDAO.class);
                bind(HorarioDAO.class).to(MyBATISHorarioDAO.class);
				bind(ReservaDAO.class).to(MyBATISReservaDAO.class);
                bind(UsuarioDAO.class).to(MyBATISUsuarioDAO.class); 
                bind(SesionLogger.class).to(ShiroLogger.class);
            }
        });

        servletContextEvent.getServletContext().setAttribute(Injector.class.getName(), injector);
    }
}