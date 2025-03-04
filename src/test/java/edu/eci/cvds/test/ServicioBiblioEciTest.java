package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEciFactory;
import edu.eci.cvds.samples.entities.Usuario;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServicioBiblioEciTest {
	
    protected ServiciosBiblioEci serviciosBiblioEci;
	protected Usuario usuario;
    protected Usuario usuario2;

    protected ServicioBiblioEciTest() throws ExcepcionServiciosBiblioEci {
        serviciosBiblioEci = ServiciosBiblioEciFactory.getInstance().getServiciosBiblioEciTesting();
		usuario = serviciosBiblioEci.consultarUsuario("a@gmail.com");
        usuario2 = serviciosBiblioEci.consultarUsuario("b@gmail.com");
    }

    protected int getLastRecursoId() throws ExcepcionServiciosBiblioEci {
        List<Recurso> recursos = serviciosBiblioEci.consultarRecurso();
        return recursos.get(recursos.size()-1).getId();
    }

    protected Date getInitialDate(int dias){
        Calendar calendarInicio = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        calendarInicio.set(year+1900,month,day+1+dias,7,0,0);
        return calendarInicio.getTime();
    }

    protected Date getInitialDateHoras(int horas){
        Calendar calendarInicio = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        calendarInicio.set(year+1900,month,day+1,7+horas,0,0);
        return calendarInicio.getTime();
    }

    protected Date getInitialDate(){
        Calendar calendarInicio = Calendar.getInstance();
        int day = calendarInicio.getTime().getDate();
        int month = calendarInicio.getTime().getMonth();
        int year = calendarInicio.getTime().getYear();
        calendarInicio.set(year+1900,month,day+1,7,0,0);
        return calendarInicio.getTime();
    }

    protected Date getFinalDate(){
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarFinal.getTime().getDate();
        int month = calendarFinal.getTime().getMonth();
        int year = calendarFinal.getTime().getYear();
        calendarFinal.set(year+1900,month,day+1,8,0,0);
        return calendarFinal.getTime();
    }

    protected Date getFinalDateDays(int dias){
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarFinal.getTime().getDate();
        int month = calendarFinal.getTime().getMonth();
        int year = calendarFinal.getTime().getYear();
        calendarFinal.set(year+1900,month,day+1+dias,8,0,0);
        return calendarFinal.getTime();
    }

    protected Date getFinalDate(int horas){
        Calendar calendarFinal = Calendar.getInstance();
        int day = calendarFinal.getTime().getDate();
        int month = calendarFinal.getTime().getMonth();
        int year = calendarFinal.getTime().getYear();
        calendarFinal.set(year+1900,month,day+1,8+horas,0,0);
        return calendarFinal.getTime();

    }

    protected Date getConcurrentDate(int numDias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getInitialDate());
        calendar.add(Calendar.DAY_OF_YEAR,numDias);
        return calendar.getTime();
    }
}
