package com.fiuba.proyectosinformaticos.oupa.pillbox.services;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.UserManager;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;
import com.fiuba.proyectosinformaticos.oupa.pillbox.NewPillStep4;
import com.fiuba.proyectosinformaticos.oupa.pillbox.PillboxActivity;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PillService {
    private OupaApi oupaApi;

    public PillService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void createNewPill(Pill pill, final NewPillStep4 delegate) {

        PillSerialized pillSerialized = new PillSerialized();
        pillSerialized.personal_medicine_reminder = new PillSerialized.Personal_medicine_reminder();
        pillSerialized.personal_medicine_reminder.name = pill.name;
        pillSerialized.personal_medicine_reminder.notes = "";//por ahora no se usa

        OUPADateFormat customDateFormat = new OUPADateFormat();
        String myFormat = customDateFormat.dateFormatForServer();
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        String hourFormat = customDateFormat.timeFormatForServer();
        SimpleDateFormat hourdf = new SimpleDateFormat(hourFormat);

        pillSerialized.personal_medicine_reminder.date = sdf.format(pill.date);
        pillSerialized.personal_medicine_reminder.time = hourdf.format(pill.date);

        oupaApi.createPill(UserManager.getInstance().getAuthorizationToken(),"application/json",pillSerialized).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("PILLSERVICE", "NEW PILL CREATED!!!");
                    delegate.onResponseSuccess();
                } else {
                    Log.e("PILLSERVICE", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("PILLSERVICE", t.getMessage());
            }
        });
    }

    public void getPillsForToday(final PillboxActivity delegate) {
        oupaApi.getPillsForToday(UserManager.getInstance().getAuthorizationToken()).enqueue(new Callback<ArrayList<PillResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PillResponse>> call, Response<ArrayList<PillResponse>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("PILLSERVICE", response.body().toString());
                        delegate.onResponseSuccess(response.body());
                    }else {
                        Log.i("PILLSERVICE", "NO RESPONSE");
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("PILLSERVICE", response.body().toString());
                    }else {
                        Log.e("PILLSERVICE", "NO RESPONSE");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PillResponse>> call, Throwable t) {
                Log.e("PILLSERVICE", t.getMessage());
            }
        });
    }
}
