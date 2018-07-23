package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillClient;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillResponse;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;
import com.fiuba.proyectosinformaticos.oupa.pillbox.views.PillAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PillboxActivity extends AppCompatActivity implements PillClient {

    private ArrayList<Pill> pillsArray;
    private Integer pillPosition;
    private PillService pillService;

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE_ADDED_PILL = 400;

    public PillboxActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pillbox);

        pillService = new PillService();


        ImageButton newPillButton = (ImageButton) findViewById(R.id.btn_pill);
        newPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PillboxActivity.this, NewPillStep1.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        setupInitials();
    }

    private void setupInitials() {
        pillsArray = new ArrayList<Pill>();
        pillService.getPillsForToday(this);

    }


    private void displayPills() {
        final ListView pillsList = (ListView) findViewById(R.id.list_of_pills);
        PillAdapter pillAdapter = new PillAdapter(this, pillsArray);
        pillsList.setAdapter(pillAdapter);
        pillsList.setSelection(this.pillsArray.size());

        pillsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                pillPosition = position;

                Pill pill = pillsArray.get(position);
                if (!pill.shouldBeDrinked()) {
                    return;
                }

                Intent intent = new Intent(PillboxActivity.this, DrinkedPillActivity.class);
                intent.putExtra("pill", pill);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                Pill pill = (Pill) data.getSerializableExtra("pill");
                pillsArray.set(pillPosition, pill);
                displayPills();

            }else if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE_ADDED_PILL){
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
                loadingView.setVisibility(View.VISIBLE);
                pillsArray = new ArrayList<Pill>();
                pillService.getPillsForToday(this);
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public PillboxActivity(ArrayList<Pill> pillsArray) {
        this.pillsArray = pillsArray;
    }

    @Override
    public void onResponseSuccess(Object responseBody) {
        ArrayList<PillResponse> pillResponseArrayList = (ArrayList<PillResponse>) responseBody;

        for (PillResponse pillResponse : pillResponseArrayList) {

            Pill pill = new Pill();
            pill.name = pillResponse.name;
            pill.drinked = pillResponse.taken;
            pill.id = pillResponse.id;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date parsedDate = dateFormat.parse(pillResponse.time);
                pill.date = parsedDate;
            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
            }

            pillsArray.add(pill);
        }

        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);
        displayPills();
    }

    public void onResponseError() {
        Toast.makeText(this, "Se produjo un error de conexión en el pastillero, intente luego",
                Toast.LENGTH_LONG).show();
        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);
        finish();

    }

}