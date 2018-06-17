package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NewPillStep4 extends AppCompatActivity {

    private  Pill pill;
    public static final int STEP_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill_step4);

        ImageButton closeButton = (ImageButton) findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pill = (Pill) getIntent().getSerializableExtra("pill");
        setupPillDescription();
    }

    private void setupPillDescription(){

        TextView pillDescriptionTextView = (TextView) findViewById(R.id.pillInformation);
        OUPADateFormat customDateFormat = new OUPADateFormat();

        DateFormat dateFormat = new SimpleDateFormat(customDateFormat.dateFormat());
        String pillDate = dateFormat.format(pill.date);
        String pillDescription = "Se agregará pastilla "+pill.name+ " para la fecha: "+pillDate + " y le notificaremos 10 minutos antes de la hora seleccionada";
        pillDescriptionTextView.setText(pillDescription);
    }

    public void confirmButtonPressed(View view) {
        Intent intent = new Intent(NewPillStep4.this, PillboxActivity.class);
        intent.putExtra("pill", pill);
        startActivityForResult(intent,STEP_CODE);
        finish();
    }


}
