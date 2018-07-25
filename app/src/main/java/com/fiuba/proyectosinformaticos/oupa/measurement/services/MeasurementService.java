package com.fiuba.proyectosinformaticos.oupa.measurement.services;

import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.measurement.MeasurementListActivity;
import com.fiuba.proyectosinformaticos.oupa.measurement.NewMeasurementStep2;
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;
import com.fiuba.proyectosinformaticos.oupa.pillbox.NewPillStep4;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillClient;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillResponse;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillSerialized;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementService {
    private OupaApi oupaApi;

    public MeasurementService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void createNewMeasurement(final Measurement measurement, final NewMeasurementStep2 delegate) {

        MeasurementSerialized measurementSerialized = new MeasurementSerialized();
        measurementSerialized.measurement = new MeasurementSerialized.Measurement();
        measurementSerialized.measurement.measurement_type = measurement.measurement_type;
        measurementSerialized.measurement.notes = "";//por ahora no se usa
        measurementSerialized.measurement.value = measurement.value;

        OUPADateFormat customDateFormat = new OUPADateFormat();
        String myFormat = customDateFormat.measureDateFormatForServer();
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        measurementSerialized.measurement.date = sdf.format(measurement.date);

        String accessToken = new UserSessionManager(delegate.getApplicationContext()).getAuthorizationToken();

        oupaApi.createMeasurement(accessToken,"application/json",measurementSerialized).enqueue(new Callback<MeasurementSerialized>() {

            @Override
            public void onResponse(Call<MeasurementSerialized> call, Response<MeasurementSerialized> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("MEASUREMENTESERVICE", "NEW MEASUREMENTE CREATED!!!");
                    delegate.onResponseSuccess(response.body());
                } else {
                    Log.e("MEASUREMENTESERVICE", response.body().toString());
                    delegate.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<MeasurementSerialized> call, Throwable t) {
                Log.e("MEASUREMENTESERVICE", t.getMessage());
                delegate.onResponseError();
            }
        });
    }

    public void getMeasurements(final MeasurementListActivity delegate) {
        String accessToken = new UserSessionManager(delegate.getApplicationContext()).getAuthorizationToken();
        oupaApi.getMeasurements(accessToken).enqueue(new Callback<ArrayList<MeasurementSerialized.Measurement>>() {
            @Override
            public void onResponse(Call<ArrayList<MeasurementSerialized.Measurement>> call, Response<ArrayList<MeasurementSerialized.Measurement>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("MEASUREMENTESERVICE", response.body().toString());
                        delegate.onResponseSuccess(response.body());
                    }else {
                        Log.i("MEASUREMENTESERVICE", "NO RESPONSE");
                        delegate.onResponseError();
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("MEASUREMENTESERVICE", response.body().toString());
                    }else {
                        Log.e("MEASUREMENTESERVICE", "NO RESPONSE");
                    }
                    delegate.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MeasurementSerialized.Measurement>> call, Throwable t) {
                delegate.onResponseError();
                Log.e("MEASUREMENTESERVICE", t.getMessage());
            }
        });
    }
}
