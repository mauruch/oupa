package com.fiuba.proyectosinformaticos.oupa.measurement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.measurement.heartRate.HeartRateMonitor;
import com.fiuba.proyectosinformaticos.oupa.measurement.heartRate.HeartRateStartActivity;
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;

public class NewMeasurementStep1 extends AppCompatActivity {

    private Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measurement_step1);
        measurement = new Measurement();
        attachEvents();
    }

    private void attachEvents() {

        LinearLayout temperatureLayout = findViewById(R.id.temperature_layout);
        temperatureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurement.measurement_type = "temperature";
                startStep2();
            }
        });

        LinearLayout glucoseLayout = findViewById(R.id.glucose_layout);
        glucoseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurement.measurement_type = "glucose";
                startStep2();
            }
        });

        LinearLayout preasureLayout = findViewById(R.id.preasure_layout);
        preasureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurement.measurement_type = "preasure";
                startStep2();
            }
        });

        LinearLayout heartRateLayout = findViewById(R.id.heart_rate_layout);
        heartRateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurement.measurement_type = "pulsations";
                Intent intent = new Intent(NewMeasurementStep1.this, HeartRateStartActivity.class);
                intent.addFlags(FLAG_ACTIVITY_FORWARD_RESULT);
                intent.putExtra("measurement", measurement);
                startActivity(intent);
                finish();

            }
        });

    }

    private void startStep2() {

        Intent intent = new Intent(NewMeasurementStep1.this, NewMeasurementStep2.class);
        intent.addFlags(FLAG_ACTIVITY_FORWARD_RESULT);
        intent.putExtra("measurement", measurement);
        startActivity(intent);
        finish();

    }

}


