package com.fiuba.proyectosinformaticos.oupa.pillbox.services;

import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.UserManager;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PillService {
    private OupaApi oupaApi;

    public PillService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void createNewPill(Pill pill) {
        oupaApi.createPill(UserManager.getInstance().getAuthorizationToken(),pill).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("NEW_PILL", "new pill created!");
                } else {
                    Log.e("NEW_PILL", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("NEW_PILL", t.getMessage());
            }
        });
    }
}
