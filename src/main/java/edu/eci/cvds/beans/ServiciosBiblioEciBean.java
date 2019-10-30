package edu.eci.cvds.beans;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;


public class ServiciosBiblioEciBean extends BasePageBean {
    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

    public void registrarRecurso(String Nombre, String ubicación, String tipo, int capacidad, int id) {
        try {
            serviciosBiblioEci.registrarRecurso(new Recurso(Nombre, ubicación, tipo, capacidad, id));
        } catch (ExcepcionServiciosBiblioEci e) {
            e.printStackTrace();
        }

    }


}