package com.fiuba.proyectosinformaticos.oupa.pillbox.services;

import android.content.Context;
import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;
import com.fiuba.proyectosinformaticos.oupa.pillbox.DrinkedPillActivity;
import com.fiuba.proyectosinformaticos.oupa.pillbox.NewPillStep4;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PillService {
    private OupaApi oupaApi;

    public PillService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void createNewPill(final Pill pill, final NewPillStep4 delegate) {

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

        String accessToken = new UserSessionManager(delegate.getApplicationContext()).getAuthorizationToken();

        oupaApi.createPill(accessToken,"application/json",pillSerialized).enqueue(new Callback<PillResponse>() {

            @Override
            public void onResponse(Call<PillResponse> call, Response<PillResponse> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("PILLSERVICE", "NEW PILL CREATED!!!");
                    delegate.onResponseSuccess(response.body());
                } else {
                    Log.e("PILLSERVICE", response.body().toString());
                    delegate.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<PillResponse> call, Throwable t) {
                Log.e("PILLSERVICE", t.getMessage());
                delegate.onResponseError();
            }
        });
    }

    public void getPillsForToday(final PillClient delegate) {
        String accessToken = new UserSessionManager(delegate.getApplicationContext()).getAuthorizationToken();
        oupaApi.getPillsForToday(accessToken).enqueue(new Callback<ArrayList<PillResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PillResponse>> call, Response<ArrayList<PillResponse>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("PILLSERVICE", response.body().toString());
                        delegate.onResponseSuccess(response.body());
                    }else {
                        Log.i("PILLSERVICE", "NO RESPONSE");
                        delegate.onResponseError();
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("PILLSERVICE", response.body().toString());
                    }else {
                        Log.e("PILLSERVICE", "NO RESPONSE");
                    }
                    delegate.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PillResponse>> call, Throwable t) {
                delegate.onResponseError();
                Log.e("PILLSERVICE", t.getMessage());
            }
        });
    }

    public void updatePillDrinked(Context applicationContext, final Pill pill, final DrinkedPillActivity drinkedPillActivity){
        PillTakenSerialized pillTakenSerialized = new PillTakenSerialized();
        pillTakenSerialized.personal_medicine_reminder = new PillTakenSerialized.Personal_medicine_reminder();
        pillTakenSerialized.personal_medicine_reminder.taken = pill.drinked;

        String accessToken = new UserSessionManager(applicationContext).getAuthorizationToken();
        oupaApi.drinkedPill(accessToken, pill.id, pillTakenSerialized).enqueue(new Callback<PillResponse>() {

            @Override
            public void onResponse(Call<PillResponse> call, Response<PillResponse> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("PILLSERVICE", "PILL " +pill.id+" UPDATED!!!");
                    drinkedPillActivity.onResponseSuccess();
                } else {
                    Log.e("PILLSERVICE", response.body().toString());
                    drinkedPillActivity.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<PillResponse> call, Throwable t) {
                Log.e("PILLSERVICE", t.getMessage());
                drinkedPillActivity.onResponseError();
            }
        });
    }
}
