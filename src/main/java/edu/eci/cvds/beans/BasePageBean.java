package edu.eci.cvds.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import com.google.inject.Injector;

/**
 * Esta clase conecta una pagina web con el servicio de bibliotecas de la escuela
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */
@SessionScoped
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

    /**
     * Inicializa el Bean de la pagina web
     */
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
        goHome();
    }

    /**
     * Envia un mensaje de operacion y actualiza el contexto de la pagina
     * @param message El mensaje de operacion
     */
    public void setMessage(String message){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
    /**
     * Envia un mensaje de error en la pagina web
     * @param message El mensaje que se va a enviar
     */
    public void setErrorMessage(String message){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    /**
     * Actualiza la pagina a la del indice de cada usuario
     */
    public void goHome(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
        } catch (IOException e) {
            setErrorMessage("Error en el servidor");
        }
    }
}