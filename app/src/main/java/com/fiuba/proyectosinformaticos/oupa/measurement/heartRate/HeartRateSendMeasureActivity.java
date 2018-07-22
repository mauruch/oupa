package com.fiuba.proyectosinformaticos.oupa.measurement.heartRate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.measurement.MeasurementListActivity;
import com.fiuba.proyectosinformaticos.oupa.measurement.NewMeasurementStep2;
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;
import com.fiuba.proyectosinformaticos.oupa.measurement.services.MeasurementService;

import java.util.Date;

public class HeartRateSendMeasureActivity extends NewMeasurementStep2 {

    private Measurement measurement;
    private MeasurementService measurementService;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_send_measure);

        measurement = (Measurement) getIntent().getSerializableExtra("measurement");

        TextView pillHourText = findViewById(R.id.title_heart_rate);
        pillHourText.setText("Medición obtenida: " + measurement.value + " pulsaciones por minuto");

        measurementService = new MeasurementService();

    }


    public void confirmHeartRateButtonPressed(View view) {
        measurement.date =  new Date();
        measurementService.createNewMeasurement(measurement,this);
    }

    public void onResponseSuccess(Object responseBody) {
        Intent intent = new Intent(HeartRateSendMeasureActivity.this, MeasurementListActivity.class);
        setResult(REQUEST_CODE, intent);

        finish();

    }

    public void onResponseError() {
        Toast.makeText(this, "Se produjo un error de conexión en el pastillero, intente luego",
                Toast.LENGTH_LONG).show();
        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);
        finish();

    }
}
