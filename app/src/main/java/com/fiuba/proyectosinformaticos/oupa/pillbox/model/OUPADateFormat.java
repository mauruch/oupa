package com.fiuba.proyectosinformaticos.oupa.pillbox.model;

public class OUPADateFormat {
    public OUPADateFormat(){
    }

    public String dateFormat(){
        return "MM/dd/yy hh:mm a";
    }

    public String dateFormatForServer(){
        return "yyyy-MM-dd";
    }

    public String timeFormatForServer(){
        return "hh:mm"; //TODO: ver am pm
    }
}
