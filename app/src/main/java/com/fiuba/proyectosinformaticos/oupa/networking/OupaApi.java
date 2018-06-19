package com.fiuba.proyectosinformaticos.oupa.networking;

import com.fiuba.proyectosinformaticos.oupa.model.UserSession;
import com.fiuba.proyectosinformaticos.oupa.model.request.UserSessionRequest;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OupaApi {

    @POST("/emergency_alarm")
    Call<Void> createSOSAlert(@Header("Authorization") String accessToken);

    @POST("/personal_medicine_reminder")
    Call<Void> createPill(@Header("Authorization") String accessToken, @Body Pill pill);

    @POST("/users/sessions")
    Call<UserSession> createUserSession(@Body UserSessionRequest sessionRequest);

}