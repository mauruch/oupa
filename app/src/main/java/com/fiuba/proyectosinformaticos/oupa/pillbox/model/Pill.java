package com.fiuba.proyectosinformaticos.oupa.pillbox.model;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pill implements Serializable{

    public String name;
    public String notes;
    public Date date;
    public boolean repeat;
    public boolean drinked;
    public String id;

    public String hourString() {
        DateFormat df = new SimpleDateFormat("hh:mm a");
        return df.format(this.date);
    }

    public boolean shouldBeDrinked(){
        Date now = new Date();
        return now.after(this.date);
    }

}
