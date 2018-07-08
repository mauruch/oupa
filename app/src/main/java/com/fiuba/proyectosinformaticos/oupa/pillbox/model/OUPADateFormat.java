package com.fiuba.proyectosinformaticos.oupa.pillbox.model;

public class OUPADateFormat {
    public OUPADateFormat() {
    }

    public String dateFormatForServer() {
        return "yyyy-MM-dd";
    }

    public String dateFormatPill() {
        return "dd/MM/yyyy";
    }

    public String timeFormatForServer() {
        return "HH:mm";
    }
}
