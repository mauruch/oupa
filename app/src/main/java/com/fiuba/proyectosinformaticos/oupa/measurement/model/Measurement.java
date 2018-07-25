package com.fiuba.proyectosinformaticos.oupa.measurement.model;

import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Measurement implements Serializable{
    public String measurement_type;
    public String value;
    public String notes;
    public Date date;

    public String dateSring() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(this.date);
    }

}
