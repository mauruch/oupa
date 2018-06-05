package com.fiuba.proyectosinformaticos.oupa.services;

import android.util.Log;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.UserManager;
import com.fiuba.proyectosinformaticos.oupa.model.UserSession;
import com.fiuba.proyectosinformaticos.oupa.model.request.UserSessionRequest;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private OupaApi oupaApi;

    public UserService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void createUserSession(String email, String password, String deviceToken) {

        UserSessionRequest userSessionRequest = new UserSessionRequest();
        userSessionRequest.session = new UserSessionRequest.Session();
        userSessionRequest.session.email = email;
        userSessionRequest.session.password = password;
        userSessionRequest.session.deviceToken = deviceToken;
        userSessionRequest.session.deviceType = "android";

         oupaApi.createUserSession(userSessionRequest).enqueue(new Callback<UserSession>() {
             @Override
             public void onResponse(Call<UserSession> call, Response<UserSession> response) {
                 if (response.code() > 199 && response.code() < 300) {
                     Log.i("Session created", response.body().accessToken);
                     UserManager.getInstance().setUserSession(response.body());
                 } else {
                     Log.e("createUserSession", response.message());
                 }
             }

             @Override
             public void onFailure(Call<UserSession> call, Throwable t) {
                 Log.e("createUserSession", t.getMessage());
             }
         });
    }
}
