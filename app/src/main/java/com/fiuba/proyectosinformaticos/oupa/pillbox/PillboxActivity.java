package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.views.PillAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PillboxActivity extends AppCompatActivity {

    private ArrayList<Pill> pillsArray;
    private Integer pillPosition;

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pillbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              //          .setAction("Action", null).show();
                Intent intent = new Intent(PillboxActivity.this, NewPillStep1.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        setupInitials();
    }

    private void setupInitials(){
        createPills();
        displayPills();
    }

    //TODO: delete this method when server is integrated
    private void createPills(){

        pillsArray = new ArrayList<Pill>();

        Pill pill1 = new Pill();
        pill1.name = "Aspirina";
        pill1.drinked = true;
        pill1.date = new Date();

        Pill pill2 = new Pill();
        pill2.name = "Aspirina 2";
        pill2.drinked = true;
        pill2.date = new Date();

        Pill pill3 = new Pill();
        pill3.name = "Aspirina 3";
        pill3.drinked = false;
        pill3.date = new Date();

        Pill pill4 = new Pill();
        pill4.name = "Alplax";
        pill4.date = new Date();

        Pill pill5 = new Pill();
        pill5.name = "Otra pasti";
        pill5.date = new Date();

        Pill pill6 = new Pill();
        pill6.name = "Otraaa";
        pill6.date = new Date();

        Pill pill7 = new Pill();
        pill7.name = "Aspirina";
        pill7.date = new Date();

        pillsArray.add(pill1);
        pillsArray.add(pill2);
        pillsArray.add(pill3);
        pillsArray.add(pill4);
        pillsArray.add(pill5);
        pillsArray.add(pill6);
        pillsArray.add(pill7);
    }

    private void displayPills() {
        final ListView pillsList = (ListView)findViewById(R.id.list_of_pills);
        PillAdapter pillAdapter = new PillAdapter(this,pillsArray);
        pillsList.setAdapter(pillAdapter);
        pillsList.setSelection(this.pillsArray.size());

        pillsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                pillPosition = position;
                Intent intent = new Intent(PillboxActivity.this, DrinkedPillActivity.class);
                intent.putExtra("pill", pillsArray.get(position));
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                //TODO: mandar al server que se tomo la pasti
                Pill pill = (Pill) data.getSerializableExtra("pill");
                pillsArray.set(pillPosition,pill);
                displayPills();

            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

}
