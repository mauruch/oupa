package com.fiuba.proyectosinformaticos.oupa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSession {
    @Expose
    @SerializedName("access_token")
    public String accessToken;
}