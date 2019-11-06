package edu.eci.cvds.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import com.google.inject.Injector;

/**
 * Esta clase conecta una pagina web con el servicio de bibliotecas de la escuela
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */
public abstract class BasePageBean implements Serializable {

    private Injector injector;

    /**
     * Retorna el inyector del servlet utilizado
     * @return El inyector del servlet utilizado
     */
    public Injector getInjector() {
        if (injector == null) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                    .getContext();
            injector = (Injector) servletContext.getAttribute(Injector.class.getName());
        }
        return injector;
    }

    /**
     * Cambia el inyector del Bean
     * @param injector El inyector del servlet utilizado
     */
    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    @PostConstruct
    public void init() {
        getInjector().injectMembers(this);
    }


}