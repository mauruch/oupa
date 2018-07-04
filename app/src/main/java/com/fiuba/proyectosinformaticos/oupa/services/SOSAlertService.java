package com.fiuba.proyectosinformaticos.oupa.services;

import android.os.UserManager;
import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.App;
import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SOSAlertService {
    private OupaApi oupaApi;

    public SOSAlertService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void sendSOSAlert() {
        String accessToken = new UserSessionManager(App.getContext()).getAuthorizationToken();
        oupaApi.createSOSAlert(accessToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("sendSOSAlert", "sos alert sent ok!!");
                } else {
                    Log.e("sendSOSAlert", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("sendSOSAlert", t.getMessage());
            }
        });
    }
}
