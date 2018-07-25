package com.fiuba.proyectosinformaticos.oupa.measurement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;
import com.fiuba.proyectosinformaticos.oupa.measurement.services.MeasurementSerialized;
import com.fiuba.proyectosinformaticos.oupa.measurement.services.MeasurementService;
import com.fiuba.proyectosinformaticos.oupa.measurement.views.MeasurementAdapter;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.views.PillAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MeasurementListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private ArrayList<Measurement> measurementArrayList;
    private MeasurementService measurementService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_list);

        measurementService = new MeasurementService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeasurementListActivity.this, NewMeasurementStep1.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        setupInitials();

    }


    private void setupInitials() {
        measurementService.getMeasurements(this);
    }


    public void onResponseSuccess(Object responseBody) {
        ArrayList<MeasurementSerialized.Measurement> measurementResponseArrayList = (ArrayList<MeasurementSerialized.Measurement>) responseBody;
        measurementArrayList = new ArrayList<Measurement>();

        for (MeasurementSerialized.Measurement measurementResponse : measurementResponseArrayList) {

            Measurement measurement = new Measurement();
            measurement.measurement_type = measurementResponse.measurement_type;
            measurement.value = measurementResponse.value;
            measurement.notes = measurementResponse.notes;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date parsedDate = dateFormat.parse(measurementResponse.date);
                measurement.date = parsedDate;
            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
            }

            measurementArrayList.add(measurement);

        }

        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);
        displayMeasurements();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == 1) {
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
                loadingView.setVisibility(View.VISIBLE);
                measurementService.getMeasurements(this);

            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }



    private void displayMeasurements() {
        final ListView measurementsList = (ListView) findViewById(R.id.list_of_measurements);
        MeasurementAdapter measurementAdapter = new MeasurementAdapter(this, measurementArrayList);
        measurementsList.setAdapter(measurementAdapter);
        measurementsList.setSelection(this.measurementArrayList.size());

    }

    public void onResponseError() {
        Toast.makeText(this, "Se produjo un error de conexión en el pastillero, intente luego",
                Toast.LENGTH_LONG).show();
        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);
        finish();

    }
}
