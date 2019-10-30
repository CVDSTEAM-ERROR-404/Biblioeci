package edu.eci.cvds.guice;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import edu.eci.cvds.security.SesionLogger;
import edu.eci.cvds.security.ShiroLogger;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import edu.eci.cvds.sampleprj.dao.EventoProgramadoDAO;
import edu.eci.cvds.sampleprj.dao.RecursoDAO;
import edu.eci.cvds.sampleprj.dao.ReservaDAO;

import edu.eci.cvds.sampleprj.dao.UsuarioDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISEventoProgramadoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISRecursoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISReservaDAO;

import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISUsuarioDAO;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.impl.ServiciosBiblioEciImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(Injector.class.getName());
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Injector injector = Guice.createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setEnvironmentId("development");
                setClassPathResource("mybatis-config.xml");

                bind(ServiciosBiblioEci.class).to(ServiciosBiblioEciImpl.class);
                bind(RecursoDAO.class).to(MyBATISRecursoDAO.class);
                bind(EventoProgramadoDAO.class).to(MyBATISEventoProgramadoDAO.class);
				bind(ReservaDAO.class).to(MyBATISReservaDAO.class);
                
                bind(UsuarioDAO.class).to(MyBATISUsuarioDAO.class);


                
                bind(SesionLogger.class).to(ShiroLogger.class);
                

            }
        });

        servletContextEvent.getServletContext().setAttribute(Injector.class.getName(), injector);
    }
}