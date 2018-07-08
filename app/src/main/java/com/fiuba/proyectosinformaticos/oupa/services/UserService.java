package com.fiuba.proyectosinformaticos.oupa.services;

import android.support.annotation.NonNull;

import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.model.UserLogged;
import com.fiuba.proyectosinformaticos.oupa.model.UserSession;
import com.fiuba.proyectosinformaticos.oupa.model.request.UserSessionRequest;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;

public class UserService {

    private OupaApi oupaApi;

    public UserService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public Call<UserSession> createUserSession(String email, String password) {

        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        UserSessionRequest userSessionRequest = getUserSessionRequest(email, password, deviceToken);

        return oupaApi.createUserSession(userSessionRequest);
    }

    public Call<UserLogged> getUserLogged(String accessToken) {
        return oupaApi.getUserLogged(accessToken);
    }

    @NonNull
    private UserSessionRequest getUserSessionRequest(String email, String password, String deviceToken) {
        UserSessionRequest userSessionRequest = new UserSessionRequest();
        userSessionRequest.session = new UserSessionRequest.Session();
        userSessionRequest.session.email = email;
        userSessionRequest.session.password = password;
        userSessionRequest.session.deviceToken = deviceToken;
        userSessionRequest.session.deviceType = "android";
        return userSessionRequest;
    }
}
