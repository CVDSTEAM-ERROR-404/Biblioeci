package edu.eci.cvds.beans;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Recurso;
import edu.eci.cvds.samples.entities.TipoRecurso;
import edu.eci.cvds.samples.services.ExcepcionServiciosBiblioEci;
import edu.eci.cvds.samples.services.ServiciosBiblioEci;
import org.apache.commons.lang3.tuple.MutablePair;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;

/**
 * Esta clase conecta la pagina web con los diferentes servicios de reportes de la aplicacion de la biblioteca
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
@ManagedBean(name="ReportesBean")
@SessionScoped
public class ReportesBean extends BasePageBean {

    @Inject
    private ServiciosBiblioEci serviciosBiblioEci;

    private BarChartModel barModel1;
    private BarChartModel barmenosModel;
    private PieChartModel barocupacionModel;
    private PieChartModel barMenorOcupacionModel;
    private PieChartModel recurrentesModel;
    private PieChartModel canceladasModel;
    private TipoRecurso tipo;
    private Date inicioFranjaHoraria;
    private Date finFranjaHoraria;
    private Date inicioRangoFechas;
    private Date finRangoFechas;
    private String programa;

    /**
     * Muestra el modelo en forma circular de las reservas canceladas
     * @return El modelo en forma circular de las reservas canceladas
     */
    public PieChartModel getCanceladasModel() {
        return canceladasModel;
    }

    /**
     * Cambia el modelo en forma circular de las reservas canceladas
     * @param canceladasModel El nuevo modelo en forma circular de las reservas canceladas
     */
    public void setCanceladasModel(PieChartModel canceladasModel) {
        this.canceladasModel = canceladasModel;
    }

    /**
     * Muestra la aplicacion de los servicios de la biblioteca
     * @return La aplicacion de los servicios de la biblioteca
     */
    public ServiciosBiblioEci getServiciosBiblioEci() {
        return serviciosBiblioEci;
    }

    /**
     * Cambia la aplicacion de los servicios de la biblioteca
     * @param serviciosBiblioEci la nueva aplicacion de los servicios de la biblioteca
     */
    public void setServiciosBiblioEci(ServiciosBiblioEci serviciosBiblioEci) {
        this.serviciosBiblioEci = serviciosBiblioEci;
    }

    /**
     * Muestra el modelo en forma circular de las reservas recurrentes
     * @return El modelo en forma circular de las reservas recurrentes
     */
    public PieChartModel getRecurrentesModel() {
        return recurrentesModel;
    }

    /**
     * Cambia el modelo en forma circular de las reservas recurrentes
     * @param recurrentesModel El nuevo modelo en forma circular de las reservas recurrentes
     */
    public void setRecurrentesModel(PieChartModel recurrentesModel) {
        this.recurrentesModel = recurrentesModel;
    }

    /**
     * Muestra el modelo en forma circular de los horarios de menor ocupacion
     * @return El modelo en forma circular de los horarios de menor ocupacion
     */
    public PieChartModel getBarMenorOcupacionModel() {
        return barMenorOcupacionModel;
    }

    /**
     * Cambia el modelo en forma circular de los horarios de menor ocupacion
     * @param barMenorOcupacionModel El nuevo modelo en forma circular de los horarios de menor ocupacion
     */
    public void setBarMenorOcupacionModel(PieChartModel barMenorOcupacionModel) {
        this.barMenorOcupacionModel = barMenorOcupacionModel;
    }

    /**
     * Muestra el modelo en forma circular de los horarios de mayor ocupacion
     * @return El modelo en forma circular de los horarios de mayor ocupacion
     */
    public PieChartModel getBarocupacionModel() {
        return barocupacionModel;
    }

    /**
     * Cambia el modelo en forma circular de los horarios de mayor ocupacion
     * @param barocupacionModel El nuevo modelo en forma circular de los horarios de mayor ocupacion
     */
    public void setBarocupacionModel(PieChartModel barocupacionModel) {
        this.barocupacionModel = barocupacionModel;
    }

    /**
     * Muestra el tipo del recurso sobre el cual se va a filtrar
     * @return El tipo del recurso sobre el cual se va a filtrar
     */
    public TipoRecurso getTipo() {
        return tipo;
    }

    /**
     * Cambia el tipo del recurso sobre el cual se va a filtrar
     * @param tipo El nuevo tipo del recurso sobre el cual se va a filtrar
     */
    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }

    /**
     * Muestra el inicio de la franja sobre la cual se va a filtrar
     * @return El inicio de la franja sobre la cual se va a filtrar
     */
    public Date getInicioFranjaHoraria() {
        return inicioFranjaHoraria;
    }

    /**
     * Cambia el inicio de la franja sobre la cual se va a filtrar
     * @param inicioFranjaHoraria El nuevo inicio de la franja sobre la cual se va a filtrar
     */
    public void setInicioFranjaHoraria(Date inicioFranjaHoraria) {
        this.inicioFranjaHoraria = inicioFranjaHoraria;
    }

    /**
     * Muestra el fin de la franja sobre la cual se va a filtrar
     * @return El fin de la franja sobre la cual se va a filtrar
     */
    public Date getFinFranjaHoraria() {
        return finFranjaHoraria;
    }

    /**
     * Cambia el fin de la franja sobre la cual se va a filtrar
     * @param finFranjaHoraria El nuevo fin de la franja sobre la cual se va a filtrar
     */
    public void setFinFranjaHoraria(Date finFranjaHoraria) {
        this.finFranjaHoraria = finFranjaHoraria;
    }

    /**
     * Muestra el inicio del rango de fechas sobre el cual se va a filtrar
     * @return El inicio del rango de fechas sobre el cual se va a filtrar
     */
    public Date getInicioRangoFechas() {
        return inicioRangoFechas;
    }

    /**
     * Cambia el inicio del rango de fechas sobre el cual se va a filtrar
     * @param inicioRangoFechas El nuevo inicio del rango de fechas sobre el cual se va a filtrar
     */
    public void setInicioRangoFechas(Date inicioRangoFechas) {
        this.inicioRangoFechas = inicioRangoFechas;
    }

    /**
     * Muestra el fin del rango de fechas sobre el cual se va a filtrar
     * @return El fin del rango de fechas sobre el cual se va a filtrar
     */
    public Date getFinRangoFechas() {
        return finRangoFechas;
    }

    /**
     * Cambia el fin del rango de fechas sobre el cual se va a filtrar
     * @param finRangoFechas El nuevo fin del rango de fechas sobre el cual se va a filtrar
     */
    public void setFinRangoFechas(Date finRangoFechas) {
        this.finRangoFechas = finRangoFechas;
    }

    /**
     * Muestra el programa del usuario sobre el cual se va a filtrar
     * @return El programa del usuario sobre el cual se va a filtrar
     */
    public String getPrograma() {
        return programa;
    }

    /**
     * Cambia el programa del usuario sobre el cual se va a filtrar
     * @param programa El nuevo programa del usuario sobre el cual se va a filtrar
     */
    public void setPrograma(String programa) {
        this.programa = programa;
        if("".equals(programa)){ this.programa = null; }
    }

    /**
     * Muestra el modelo en forma de barras de los recursos mas usados
     * @return El modelo en forma de barras de los recursos mas usados
     */
    public BarChartModel getBarModel1() {
        return barModel1;
    }

    /**
     * Cambia el modelo en forma de barras de los recursos mas usados
     * @param barModel El nuevo modelo en forma de barras de los recursos mas usados
     */
    public void setBarModel1(BarChartModel barModel) {
        this.barModel1 = barModel;
    }

    /**
     * Muestra el modelo en forma de barras de los recursos menos usados
     * @return El modelo en forma de barras de los recursos menos usados
     */
    public BarChartModel getBarmenosModel() {
        return barmenosModel;
    }

    /**
     * Cambia el modelo en forma de barras de los recursos menos usados
     * @param barmenosModel El nuevo modelo en forma de barras de los recursos menos usados
     */
    public void setBarmenosModel(BarChartModel barmenosModel) {
        this.barmenosModel = barmenosModel;
    }

    /**
     * Consruye el modelo de barras de los recursos mas usados
     * @return El modelo de barras de los recursos mas usados
     */
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

    /**
     * Construye el modelo de barras de los recursos menos usados
     * @return El modelo de barras de los recursos menos usados
     */
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

    /**
     * Inicializa todos los modelos de la pgina de reportes
     */
    public void createModels(){
        createmasModel();
        createmenosModel();
        createOcupacionModel();
        createMenorOcupacionModel();
        createRecurrentesModel();
        createCanceladasModel();
    }

    /**
     * Inicializa el modelo de barras de los recursos mas usados
     */
    public void createmasModel() {
        barModel1 = initmasModel();

        barModel1.setTitle("recursos mas usados");
        barModel1.setLegendPosition("ne");

        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel("Recurso");

        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Ocupaciones");

    }

    /**
     * Inicializa el modelo de barras de los recursos menos usados
     */
    public void createmenosModel() {
        barmenosModel = initmenosModel();
        barmenosModel.setTitle("recursos menos usados");
        barmenosModel.setLegendPosition("ne");
        Axis xAxis = barmenosModel.getAxis(AxisType.X);
        xAxis.setLabel("Recurso");
        Axis yAxis = barmenosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Ocupaciones");
    }

    /**
     * Inicializa el modelo circula rde de los horarios mas ocupados
     */
    public  void createOcupacionModel() {
        barocupacionModel = new PieChartModel();
        int i=0;
        int valorOtros=0;
        for(MutablePair<String,Long> info : consultarHorariosMayorOcupacion()){
            if(i<=9){
                barocupacionModel.set(info.getLeft(),info.getRight());
            }
            else valorOtros+=info.getRight();
            i++;
        }
        barocupacionModel.set("Otros",valorOtros);
        barocupacionModel.setTitle("Horarios mayor ocupacion");
        barocupacionModel.setLegendPosition("w");
        barocupacionModel.setShadow(false);
    }

    /**
     * Inicializa el modelo circular de de los horarios menos ocupados
     */
    public  void createMenorOcupacionModel() {
        barMenorOcupacionModel = new PieChartModel();
        int i=0;
        int valorOtros=0;
        for(MutablePair<String,Long> info : consultarHorariosMenorOcupacion()){
            if(i<=9){
                barMenorOcupacionModel.set(info.getLeft(),info.getRight());
            }
            else valorOtros+=info.getRight();
            i++;
        }
        barMenorOcupacionModel.set("Otros",valorOtros);
        barMenorOcupacionModel.setTitle("Horarios menor ocupacion");
        barMenorOcupacionModel.setLegendPosition("w");
        barMenorOcupacionModel.setShadow(false);
    }

    /**
     * Inicializa el modelo circular de de las resevas recurrentes
     */
    public  void createRecurrentesModel() {
        recurrentesModel = new PieChartModel();
        MutablePair<Integer, Integer> info = consultarReservasRecurrentes();
        recurrentesModel.set("Recurrentes",info.getLeft());
        recurrentesModel.set("Simples",info.getRight());
        recurrentesModel.setTitle("Reservas Recurrentes vs Simples");
        recurrentesModel.setLegendPosition("w");
        recurrentesModel.setShadow(false);
    }

    /**
     * Inicializa el modelo circular de de las resevas canceladas
     */
    public  void createCanceladasModel() {
        canceladasModel = new PieChartModel();
        MutablePair<Integer, Integer> info = consultarReservasCanceladas();
        canceladasModel.set("Canceladas",info.getLeft());
        canceladasModel.set("Atcivas",info.getRight());
        canceladasModel.setTitle("Reservas Canceladas vs Activas");
        canceladasModel.setLegendPosition("w");
        canceladasModel.setShadow(false);
    }

    /**
     * Genera la franja horaria con los datos suministrados en la pagina web
     * @return La franja horaria con los datos suministrados en la pagina web
     */
    private MutablePair<Date,Date> crearFranjaHoraria(Date inicioFranja,Date finFranja){
        MutablePair<Date,Date> franjaHoraria=null;
        if(inicioFranja!=null && finFranja!=null){
            franjaHoraria=new MutablePair<Date, Date>(inicioFranja,finFranja);
        }
        return franjaHoraria;
    }

    /**
     * Genera el rango de fechas con los datos suministrados en la pagina web
     * @return El rango de fechas con los datos suministrados en la pagina web
     */
    private MutablePair<Date,Date> crearRango(Date inicioRango,Date finRango){
        MutablePair<Date,Date> rangoFechas=null;
        if(inicioRango!=null && finRango!=null){
            rangoFechas=new MutablePair<Date, Date>(inicioRango,finRango);
        }
        return rangoFechas;
    }

    /**
     * Retorna una lista con los recursos más usados. Si no se especifica franja horaria ni rango de fechas,
     * se discriminará por la cantidad de reservas activas, de lo contrario por la cantidad de eventos activos
     * que cumplen las especificaciones
     * @return Lista de los recursos más usados
     */
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

    /**
     * Retorna una lista con los recursos menos usados. Si no se especifica franja horaria ni rango de fechas,
     * se discriminará por la cantidad de reservas activas, de lo contrario por la cantidad de eventos activos
     * que cumplen las especificaciones
     * @return Lista de los recursos menos usados
     */
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

    /**
     * Retorna el rango de horarios de mayor ocupación de los recursos y el número de
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @return Lista de los horarios de mayor ocupación
     */
    public List<MutablePair<String,Long>>  consultarHorariosMayorOcupacion(){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);
        List<MutablePair<String,Long>> topHorarios=null;
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMayorOcupacion(tipo,franjaHoraria,rangoFechas,programa);

        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }

    /**
     * Retorna el rango de horarios de menor ocupación de los recursos y el número de
     * ocupaciones en ese rango(se tiene en cuenta los eventos para ello)
     * @return Lista de los horarios de menor ocupación
     */
    public List<MutablePair<String,Long>>  consultarHorariosMenorOcupacion(){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);
        List<MutablePair<String,Long>> topHorarios=null;
        try {
            topHorarios=serviciosBiblioEci.consultarHorariosMenorOcupacion(tipo,franjaHoraria,rangoFechas,programa);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return topHorarios;
    }


    /**
     * Retorna las reservas recurrentes con los filtros especificados
     * @return Reservas recurrentes con los filtros dados
     */
    public MutablePair<Integer, Integer> consultarReservasRecurrentes(){
        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);
        MutablePair<Integer,Integer> reservasTipo=null;
        try {
            reservasTipo = serviciosBiblioEci.consultarReservasRecurrentes(null, programa, tipo,franjaHoraria,rangoFechas);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return reservasTipo;
    }

    /**
     * Retorna las reservas canceladas con los filtros especificados
     * @return Reservas canceladas con los filtros dados
     */
    public MutablePair<Integer, Integer> consultarReservasCanceladas(){

        MutablePair<Date,Date> franjaHoraria=crearFranjaHoraria(inicioFranjaHoraria,finFranjaHoraria);
        MutablePair<Date,Date> rangoFechas=crearRango(inicioRangoFechas,finRangoFechas);
        MutablePair<Integer,Integer> reservasEstado=null;
        try {
            reservasEstado = serviciosBiblioEci.consultarReservasCanceladas(null, programa, tipo,franjaHoraria,rangoFechas);
        } catch (ExcepcionServiciosBiblioEci excepcionServiciosBiblioEci) {
            setErrorMessage(excepcionServiciosBiblioEci.getMessage());
        }
        return reservasEstado;
    }
}
