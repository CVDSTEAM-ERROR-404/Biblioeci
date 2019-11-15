package edu.eci.cvds.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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


    /**
     * Envia un mensaje de operacion exitosa y actualiza la pagina
     * @param message El mensaje de operacion exitosa
     */
    public void successOperation(String message){
        setMessage(message);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login1.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setMessage(String message){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
    /**
     * Envia un mensaje de error en la pagina web
     * @param message El mensaje que se va a enviar
     */
    public void setErrorMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
}