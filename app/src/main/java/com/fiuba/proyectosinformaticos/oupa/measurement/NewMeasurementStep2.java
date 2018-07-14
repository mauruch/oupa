package com.fiuba.proyectosinformaticos.oupa.measurement;

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
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;
import com.fiuba.proyectosinformaticos.oupa.measurement.services.MeasurementService;
import com.fiuba.proyectosinformaticos.oupa.pillbox.NewPillStep4;
import com.fiuba.proyectosinformaticos.oupa.pillbox.PillboxActivity;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fiuba.proyectosinformaticos.oupa.pillbox.NewPillStep2.STEP_CODE;

public class NewMeasurementStep2 extends AppCompatActivity {

    private Measurement measurement;
    private MeasurementService measurementService;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measurement_step2);
        measurement = (Measurement) getIntent().getSerializableExtra("measurement");
        measurementService = new MeasurementService();
    }

    public void confirmButtonPressed(View view) {
        TextView measurementInput = (TextView) findViewById(R.id.valueInput);
        String measurementValue = measurementInput.getText().toString();
        if (measurementValue.equals("")) {
            Snackbar.make(view, "Ingrese el valor obtenido", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        measurement.value = measurementValue;
        measurement.date =  new Date();
        measurementService.createNewMeasurement(measurement,this);


    }

    public void onResponseSuccess(Object responseBody) {
        Intent intent = new Intent(NewMeasurementStep2.this, MeasurementListActivity.class);
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
