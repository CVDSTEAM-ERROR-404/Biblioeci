package edu.eci.cvds.beans;


import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import org.apache.commons.lang3.tuple.MutablePair;

import org.primefaces.model.chart.*;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;


@ManagedBean(name="ReportesBean")
@SessionScoped
public class ReportesBean extends BasePageBean {



    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

    private BarChartModel barModel1;

    private BarChartModel barmenosModel;


    public BarChartModel getBarModel1() {
        return barModel1;
    }

    public void setBarModel1(BarChartModel barModel) {
        this.barModel1 = barModel;
    }

    public BarChartModel getBarmenosModel() {
        return barmenosModel;
    }

    public void setBarmenosModel(BarChartModel barmenosModel) {
        this.barmenosModel = barmenosModel;
    }

    private BarChartModel initmasModel() {
        BarChartModel model = new BarChartModel();
        ChartSeries recursos = new ChartSeries();
        recursos.setLabel("recursos mas ocupados");
        for(MutablePair<Recurso,Long> info : consultarRecursosMasUsados(null,null,null,null,null,null) ){
            recursos.set(info.getLeft().getNombre(),info.getRight());
        }

        model.addSeries(recursos);

        return model;
    }

    private BarChartModel initmenosModel() {
        BarChartModel model = new BarChartModel();
        ChartSeries recursos = new ChartSeries();
        recursos.setLabel("recursos mas ocupados");
        for(MutablePair<Recurso,Long> info : consultarRecursosMenosUsados(null,null,null,null,null,null) ){
            recursos.set(info.getLeft().getNombre(),info.getRight());
        }

        model.addSeries(recursos);

        return model;
    }

    public void createModels(){
        createmasModel();
        createmenosModel();
    }
    public void createmasModel() {
        barModel1 = initmasModel();

        barModel1.setTitle("recursos mas usados");
        barModel1.setLegendPosition("ne");

        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel("Recurso");

        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Ocupaciones");

    }

    public void createmenosModel() {
        barmenosModel = initmenosModel();

        barmenosModel.setTitle("recursos menos usados");
        barmenosModel.setLegendPosition("ne");

        Axis xAxis = barmenosModel.getAxis(AxisType.X);
        xAxis.setLabel("Recurso");

        Axis yAxis = barmenosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Ocupaciones");

    }

    private MutablePair<Date,Date> crearFranjaHoraria(Date inicioFranja,Date finFranja){
        MutablePair<Date,Date> franjaHoraria=null;
        if(inicioFranja!=null && finFranja!=null){
            franjaHoraria=new MutablePair<Date, Date>(inicioFranja,finFranja);
        }
        return franjaHoraria;
    }
    private MutablePair<Date,Date> crearRango(Date inicioRango,Date finRango){
        MutablePair<Date,Date> rangoFechas=null;
        if(inicioRango!=null && finRango!=null){
            rangoFechas=new MutablePair<Date, Date>(inicioRango,finRango);
        }
        return rangoFechas;
    }
    public List<MutablePair<Recurso,Long>>  consultarRecursosMasUsados(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);
        List<MutablePair<Recurso,Long>> topRecursos=null;
        try {
            topRecursos=serviciosBiblioEci.consultarRecursosMasUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topRecursos;
    }
    public List<MutablePair<Recurso,Long>>  consultarRecursosMenosUsados(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);
        List<MutablePair<Recurso,Long>> topRecursos=null;
        try {
            topRecursos=serviciosBiblioEci.consultarRecursosMenosUsados(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topRecursos;
    }
    public List<MutablePair<String,Long>>  consultarHorariosMayorOcupacion(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);List<MutablePair<String,Long>> topHorarios=null;
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMayorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }
    public List<MutablePair<String,Long>>  consultarHorariosMenorOcupacion(TipoRecurso tipo, Date inicioFranjaHoraria, Date finFranjaHoraria, Date inicioRangoFechas, Date finRangoFechas, String programa){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);List<MutablePair<String,Long>> topHorarios=null;
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMenorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }
}
