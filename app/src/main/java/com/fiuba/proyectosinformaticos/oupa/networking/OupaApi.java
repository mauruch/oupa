package com.fiuba.proyectosinformaticos.oupa.networking;

import com.fiuba.proyectosinformaticos.oupa.model.UserSession;
import com.fiuba.proyectosinformaticos.oupa.model.request.UserSessionRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OupaApi {

    @POST("/emergency_alarm")
    Call<Void> createSOSAlert(@Header("Authorization") String accessToken);

    @POST("/users/sessions")
    Call<UserSession> createUserSession(@Body UserSessionRequest sessionRequest);

}