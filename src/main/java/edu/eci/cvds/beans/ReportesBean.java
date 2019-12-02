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

    private PieChartModel barocupacionModel;



    private PieChartModel barMenorOcupacionModel;

    private TipoRecurso tipo;
    private Date inicioFranjaHoraria;
    private Date finFranjaHoraria;
    private Date inicioRangoFechas;
    private Date finRangoFechas;
    private String programa;

    public PieChartModel getBarMenorOcupacionModel() {
        return barMenorOcupacionModel;
    }

    public void setBarMenorOcupacionModel(PieChartModel barMenorOcupacionModel) {
        this.barMenorOcupacionModel = barMenorOcupacionModel;
    }

    public PieChartModel getBarocupacionModel() {
        return barocupacionModel;
    }

    public void setBarocupacionModel(PieChartModel barocupacionModel) {
        this.barocupacionModel = barocupacionModel;
    }



    public TipoRecurso getTipo() {
        return tipo;
    }

    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }

    public Date getInicioFranjaHoraria() {
        return inicioFranjaHoraria;
    }

    public void setInicioFranjaHoraria(Date inicioFranjaHoraria) {
        this.inicioFranjaHoraria = inicioFranjaHoraria;
    }

    public Date getFinFranjaHoraria() {
        return finFranjaHoraria;
    }

    public void setFinFranjaHoraria(Date finFranjaHoraria) {
        this.finFranjaHoraria = finFranjaHoraria;
    }

    public Date getInicioRangoFechas() {
        return inicioRangoFechas;
    }

    public void setInicioRangoFechas(Date inicioRangoFechas) {
        this.inicioRangoFechas = inicioRangoFechas;
    }

    public Date getFinRangoFechas() {
        return finRangoFechas;
    }

    public void setFinRangoFechas(Date finRangoFechas) {
        this.finRangoFechas = finRangoFechas;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
        if(programa.equals("")){ this.programa = null; }
    }

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
        for(MutablePair<Recurso,Long> info : consultarRecursosMasUsados() ){
            recursos.set(info.getLeft().getNombre(),info.getRight());
        }

        model.addSeries(recursos);

        return model;
    }

    private BarChartModel initmenosModel() {
        BarChartModel model = new BarChartModel();
        ChartSeries recursos = new ChartSeries();
        recursos.setLabel("recursos menos ocupados");
        for(MutablePair<Recurso,Long> info : consultarRecursosMenosUsados() ){
            recursos.set(info.getLeft().getNombre(),info.getRight());
        }

        model.addSeries(recursos);

        return model;
    }



    public void createModels(){
        createmasModel();
        createmenosModel();
        createOcupacionModel();
        createMenorOcupacionModel();

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

    public  void createOcupacionModel() {

        barocupacionModel = new PieChartModel();
        for(MutablePair<String,Long> info : consultarHorariosMayorOcupacion()){
            barocupacionModel.set(info.getLeft(),info.getRight());
        }



        barocupacionModel.setTitle("Horarios mayor ocupacion");
        barocupacionModel.setLegendPosition("w");
        barocupacionModel.setShadow(false);

    }

    public  void createMenorOcupacionModel() {

        barMenorOcupacionModel = new PieChartModel();
        for(MutablePair<String,Long> info : consultarHorariosMenorOcupacion()){
            barMenorOcupacionModel.set(info.getLeft(),info.getRight());
        }



        barMenorOcupacionModel.setTitle("Horarios menor ocupacion");
        barMenorOcupacionModel.setLegendPosition("w");
        barMenorOcupacionModel.setShadow(false);

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
    public List<MutablePair<Recurso,Long>>  consultarRecursosMasUsados(){
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
    public List<MutablePair<Recurso,Long>>  consultarRecursosMenosUsados(){
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
    public List<MutablePair<String,Long>>  consultarHorariosMayorOcupacion(){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);List<MutablePair<String,Long>> topHorarios=null;
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMayorOcupacion(tipo,franjaHoraria,rangoFechas,programa);

        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }
    public List<MutablePair<String,Long>>  consultarHorariosMenorOcupacion(){
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
