package com.fiuba.proyectosinformaticos.oupa.pillbox.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class PillResponse {

    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("notes")
    public String notes;
    @Expose
    @SerializedName("date")
    public Timestamp date;
    @Expose
    @SerializedName("time")
    public Timestamp time;

    @SerializedName("taken")
    public boolean taken;
}
