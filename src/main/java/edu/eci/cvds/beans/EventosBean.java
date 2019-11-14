package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="EventosBean")

@SessionScoped
public class EventosBean extends BasePageBean{
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

}
