package edu.eci.cvds.beans;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleRenderingMode;
import java.util.Date;
import java.util.Map;
/**
 * Esta clase genera los eventos utilizados en el calendario de al pagina web
 * @author: CVDSTEAM-ERROR-404
 * @version: 2/12/2019
 */
public class CustomScheduleEvent implements ScheduleEvent {

    private String id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String styleClass;
    private Object data;
    private String url;
    private String description;
    private boolean allDay;
    private boolean editable;

    /**
     * Constructor por defecto de la clase CustomScheduleEvent
     */
    public CustomScheduleEvent() {
    }

    /**
     * Constructor de la clase CustomScheduleEvent
     * @param title Titulo del evento del calendario
     * @param start Fecha de Inicio del Evento
     * @param end Fecha de Fin del Evento
     * @param styleClass Estilo de Clase del Evento
     * @param data Informacion del evento
     */
    public CustomScheduleEvent(String title, Date start, Date end, String styleClass, Object data) {
        this.title = title;
        this.startDate = start;
        this.endDate = end;
        this.styleClass = styleClass;
        this.data = data;
    }

    /**
     * Muestra el identificador del evento
     * @return El identificador del evento
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Cambia el identificador del evento
     * @param id El nuevo identificador del evento
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Muestra el titulo del evento
     * @return El titulo del evento
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Cambia el titulo del evento
     * @param title El nuevo titulo del evento
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Muestra la fecha de inicio del evento
     * @return La fecha de inicio del evento
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Cambia la fecha de inicio del evento
     * @param startDate La nueva fecha de inicio del evento
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Muestra la fecha de fin del evento
     * @return La fecha de fin  del evento
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Cambia la fecha de fin del evento
     * @param endDate La nueva fecha de fin del evento
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Muestra el estilo del evento
     * @return El estilo del evento
     */
    @Override
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * Cambia el estilo del evento
     * @param styleClass El nueva estilo del evento
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Muestra la informacion del evento
     * @return La informacion del evento
     */
    @Override
    public Object getData() {
        return data;
    }

    /**
     * Cambia la informacion del evento
     * @param data La nueva informacion del evento
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Muestra si el evento dura todo el dia
     * @return El valor booleano que muestra si el evento dura todo el dia
     */
    @Override
    public boolean isAllDay() {
        return allDay;
    }

    /**
     * Determina si el evento dura todo el dia
     * @param allDay el valor booleano que determina si el evento dura todo el dia
     */
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    /**
     * Muestra la url del evento
     * @return La url del evento
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Determina la forma de redenderizar del evento
     * @return La forma de redenderizar del evento
     */
    @Override
    public ScheduleRenderingMode getRenderingMode() {
        return null;
    }

    /**
     * Determina las propiedades dinamicas del evento
     * @return Las propiedades dinamicas del evento
     */
    @Override
    public Map<String, Object> getDynamicProperties() {
        return null;
    }

    /**
     * Cambia la url del evento
     * @param url La nueva url del evento
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Muestra la descripcion del evento
     * @return La descripcion del evento
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Cambia la descripcion del evento
     * @param description La nueva descripcion del evento
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Muestra si el evento es editable
     * @return El valor booleano que muestra si el evento es editable
     */
    @Override
    public boolean isEditable() {
        return editable;
    }

    /**
     * Determina si el evento es editable
     * @param editable El valor booleano que determina si el evento es editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}


