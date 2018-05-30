package com.fiuba.proyectosinformaticos.oupa.networking;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OupaApi {

    @POST("/emergency_alert")
    Call<Void> createSOSAlert(@Header("Authorization") String lang);
}