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
    public String date;
    @Expose
    @SerializedName("time")
    public String time;

    @SerializedName("taken")
    public boolean taken;

    @SerializedName("id")
    public String id;
}
