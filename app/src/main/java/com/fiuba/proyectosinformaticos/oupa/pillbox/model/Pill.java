package com.fiuba.proyectosinformaticos.oupa.pillbox.model;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pill{

    public String name;
    public String notes;
    public Date date;
    public boolean repeat;

    public String hourString() {
        DateFormat df = new SimpleDateFormat("hh:'00' a");
        return df.format(this.date);
    }

}
