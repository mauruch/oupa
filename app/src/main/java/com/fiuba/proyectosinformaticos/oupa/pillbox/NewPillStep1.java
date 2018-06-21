package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;


public class NewPillStep1 extends AppCompatActivity {

    public static final int STEP_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill_step1);
    }

    public void confirmButtonPressed(View view) {
        TextView pillNameInput = (TextView) findViewById(R.id.newPillInput);
        String pillName = pillNameInput.getText().toString();
        if (pillName.equals("")) {
            //TODO: ver si se puede hacer el snackbar custom y con letra grande
            Snackbar.make(view, "Ingrese el nombre de la medicina", Snackbar.LENGTH_LONG)
                      .setAction("Action", null).show();
            return;
        }

        Pill pill = new Pill();
        pill.name = pillName;

        Intent intent = new Intent(NewPillStep1.this, NewPillStep2.class);
        intent.addFlags(FLAG_ACTIVITY_FORWARD_RESULT);
        intent.putExtra("pill", pill);
        startActivity(intent);
        finish();
    }


}
