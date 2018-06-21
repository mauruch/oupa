package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NewPillStep4 extends AppCompatActivity {

    private  Pill pill;
    public static final int STEP_CODE = 400;
    private  PillService pillService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill_step4);

        pillService = new PillService();

        pill = (Pill) getIntent().getSerializableExtra("pill");
        setupPillDescription();
    }

    private void setupPillDescription(){

        TextView pillName = findViewById(R.id.pill_name);
        pillName.setText(pill.name);

        OUPADateFormat customDateFormat = new OUPADateFormat();

        DateFormat hourFormat = new SimpleDateFormat(customDateFormat.timeFormatForServer());
        TextView pillHourText = findViewById(R.id.pill_hour_info);
        String hours = hourFormat.format(pill.date);
        pillHourText.setText("Hora: " + hours);

        DateFormat dateFormat = new SimpleDateFormat(customDateFormat.dateFormatPill());
        TextView pillDateText = findViewById(R.id.pill_date_info);
        String date = dateFormat.format(pill.date);
        pillDateText.setText("Fecha: " + date);

    }

    public void confirmButtonPressed(View view) {
        ProgressBar loadingView = findViewById(R.id.loading);
        loadingView.setVisibility(View.VISIBLE);
        pillService.createNewPill(pill,this);
    }

    public void onResponseSuccess(){
        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(NewPillStep4.this, PillboxActivity.class);
        setResult(STEP_CODE, intent);
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
