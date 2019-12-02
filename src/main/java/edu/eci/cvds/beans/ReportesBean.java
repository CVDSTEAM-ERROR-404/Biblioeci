package edu.eci.cvds.beans;


import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import org.apache.commons.lang3.tuple.MutablePair;
import org.primefaces.component.sticky.Sticky;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;


@ManagedBean(name="ReportesBean")
@SessionScoped
public class ReportesBean extends BasePageBean {

    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

    public List<MutablePair<Recurso,Long>>  consultarRecursosMasUsados(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=null;
        MutablePair<Date,Date> rangoFechas=null;
        List<MutablePair<Recurso,Long>> topRecursos=null;
        if(inicioFranjaHoraria!=null && finFranjaHoraria!=null) franjaHoraria=new MutablePair<Date, Date>(inicioFranjaHoraria,finFranjaHoraria);
        if(inicioRangoFechas!=null && finRangoFechas!=null) rangoFechas=new MutablePair<Date, Date>(inicioRangoFechas,finRangoFechas);
        try {
            topRecursos=serviciosBiblioEci.consultarRecursosMasUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topRecursos;
    }
    public List<MutablePair<Recurso,Long>>  consultarRecursosMenosUsados(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=null;
        MutablePair<Date,Date> rangoFechas=null;
        List<MutablePair<Recurso,Long>> topRecursos=null;
        if(inicioFranjaHoraria!=null && finFranjaHoraria!=null) franjaHoraria=new MutablePair<Date, Date>(inicioFranjaHoraria,finFranjaHoraria);
        if(inicioRangoFechas!=null && finRangoFechas!=null) rangoFechas=new MutablePair<Date, Date>(inicioRangoFechas,finRangoFechas);
        try {
            topRecursos=serviciosBiblioEci.consultarRecursosMenosUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topRecursos;
    }
    public List<MutablePair<String,Long>>  consultarHorariosMayorOcupacion(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=null;
        MutablePair<Date,Date> rangoFechas=null;
        List<MutablePair<String,Long>> topHorarios=null;
        if(inicioFranjaHoraria!=null && finFranjaHoraria!=null) franjaHoraria=new MutablePair<Date, Date>(inicioFranjaHoraria,finFranjaHoraria);
        if(inicioRangoFechas!=null && finRangoFechas!=null) rangoFechas=new MutablePair<Date, Date>(inicioRangoFechas,finRangoFechas);
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMayorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }
    public List<MutablePair<String,Long>>  consultarHorariosMenorOcupacion(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=null;
        MutablePair<Date,Date> rangoFechas=null;
        List<MutablePair<String,Long>> topHorarios=null;
        if(inicioFranjaHoraria!=null && finFranjaHoraria!=null) franjaHoraria=new MutablePair<Date, Date>(inicioFranjaHoraria,finFranjaHoraria);
        if(inicioRangoFechas!=null && finRangoFechas!=null) rangoFechas=new MutablePair<Date, Date>(inicioRangoFechas,finRangoFechas);
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMenorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }
}
