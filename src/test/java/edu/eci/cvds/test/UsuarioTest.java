package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Usuario;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mybatis.guice.transactional.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest extends ServicioBiblioEciTest{
    public UsuarioTest() throws ExcepcionServiciosBiblioEci {
    }

    @Test
    public void shouldConsultAExistentUser() throws ExcepcionServiciosBiblioEci {
        Usuario respuesta = serviciosBiblioEci.consultarUsuario("a@gmail.com");
        assertEquals(usuario,respuesta);
    }

    @Test
    public void shouldReturnNullWhenConsultAUnexistentUser() throws ExcepcionServiciosBiblioEci {
        Usuario usuario = serviciosBiblioEci.consultarUsuario("b@gmail.com");
        assertNull(usuario);
    }

    @Test
    public void shouldReturnNullWhenConsultAUserWithNullMail() throws ExcepcionServiciosBiblioEci {
        Usuario usuario = serviciosBiblioEci.consultarUsuario(null);
        assertNull(usuario);
    }
}
