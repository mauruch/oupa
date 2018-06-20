package com.fiuba.proyectosinformaticos.oupa.networking;

import com.fiuba.proyectosinformaticos.oupa.model.UserSession;
import com.fiuba.proyectosinformaticos.oupa.model.request.UserSessionRequest;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillResponse;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillSerialized;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OupaApi {

    @POST("/emergency_alarm")
    Call<Void> createSOSAlert(@Header("Authorization") String accessToken);

    @POST("/personal_medicine_reminder")
    Call<Void> createPill(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Body PillSerialized pillSerialized);

    @GET("/personal_medicine_reminder")
    Call<ArrayList<PillResponse>> getPillsForToday(@Header("Authorization") String accessToken);

    @POST("/users/sessions")
    Call<UserSession> createUserSession(@Body UserSessionRequest sessionRequest);

}