package edu.eci.cvds.samples.entities;

import java.util.Calendar;

/**
 * Enumeracion de tipos de reservas utilizadas dentro de la biblioteca de la Escuela Colombiana de Ingenieria Julio Garavito
 * @author: CVDSTEAM-ERROR-404
 * @version: 5/11/2019
 */
public enum TipoReserva {
    Simple(0), Recurrente_Diaria(Calendar.DATE), Recurrente_Semanal(Calendar.WEEK_OF_YEAR), Recurrente_Mensual(Calendar.MONTH);

    private int calendarConstant;

    TipoReserva(int calendarConstant){
        this.calendarConstant = calendarConstant;
    }
    public int getCalendarConstant() {
        return calendarConstant;
    }
}
