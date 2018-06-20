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
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillResponse;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;
import com.fiuba.proyectosinformaticos.oupa.pillbox.views.PillAdapter;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PillboxActivity extends AppCompatActivity {

    private ArrayList<Pill> pillsArray;
    private Integer pillPosition;
    private PillService pillService;

    public static final int REQUEST_CODE = 1;

    public PillboxActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pillbox);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pillService = new PillService();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PillboxActivity.this, NewPillStep1.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        setupInitials();
    }

    private void setupInitials(){
        pillsArray = new ArrayList<Pill>();
        pillService.getPillsForToday(this);

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

    public PillboxActivity(ArrayList<Pill> pillsArray) {
        this.pillsArray = pillsArray;
    }

    public void onResponseSuccess(ArrayList<PillResponse> pillResponseArrayList){

        for (PillResponse pillResponse : pillResponseArrayList) {

            Pill pill = new Pill();
            pill.name = pillResponse.name;
            pill.drinked = pillResponse.taken;

            //Necesito agregarle las 3 horas de la zona horaria al timestamp
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(pillResponse.time.getTime()));
            cal.add(Calendar.HOUR_OF_DAY, 3);
            pill.date = cal.getTime();

            pillsArray.add(pill);
        }
        displayPills();

    }

}
