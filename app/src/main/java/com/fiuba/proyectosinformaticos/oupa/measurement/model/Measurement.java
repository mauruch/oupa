package com.fiuba.proyectosinformaticos.oupa.measurement.model;

import java.io.Serializable;
import java.util.Date;

public class Measurement implements Serializable{
    public String measurement_type;
    public String value;
    public String notes;
    public Date date;
}
