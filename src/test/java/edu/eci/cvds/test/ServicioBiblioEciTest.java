package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import edu.eci.cvds.samples.entities.Usuario;

import java.util.List;

public class ServicioBiblioEciTest {
	
    protected ServiciosBiblioEci serviciosBiblioEci;
	protected Usuario usuario;

    protected ServicioBiblioEciTest() throws ExcepcionServiciosBiblioEci {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
		usuario = serviciosBiblioEci.consultarUsuario("a@gmail.com");
    }

    protected int getLastRecursoId() throws ExcepcionServiciosBiblioEci {
        List<Recurso> recursos = serviciosBiblioEci.consultarRecurso();
        return recursos.get(recursos.size()-1).getId();
    }
}
