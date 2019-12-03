package edu.eci.cvds.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Esta clase filtra los roles dentro de la aplicacion de la base de datos de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public class AnyOfRolesAuthorizationFilter extends RolesAuthorizationFilter {

    /**
     * Determina si el usuario que utiliza la aplicacion tiene acceso al recurso del servlet
     * @param request El servlet de solicitud de la pagina web
     * @param response El servlet de respuesta de la pagina web
     * @param mappedValue El valor que representa los roles permitidos para el recurso
     * @return El valor booleano que determina si el usuario que utiliza la aplicacion tiene acceso al recurso del servlet
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        final Subject subject = getSubject(request, response);
        final String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String roleName : rolesArray) {
            if (subject.hasRole(roleName)) {
                return true;
            }
        }
        return false;
    }
}


