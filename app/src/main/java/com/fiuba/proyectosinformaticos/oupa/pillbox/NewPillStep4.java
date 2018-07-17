package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillClient;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillResponse;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NewPillStep4 extends AppCompatActivity implements PillClient {

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

    public void onResponseSuccess(Object responseBody) {

        PillResponse pillResponse = (PillResponse) responseBody;
        scheduleNotification(pillResponse.id);

        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(NewPillStep4.this, PillboxActivity.class);
        setResult(STEP_CODE, intent);
        finish();
    }

    public void scheduleNotification(String pillId) {
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);

        pill.id = pillId;
        Bundle args = new Bundle();

        //TODO: deberia ser pill
        args.putSerializable("pillForNotification",pill);
        notificationIntent.putExtra("DATA",args);

        Calendar cal = Calendar.getInstance();
        cal.setTime(pill.date);
        cal.add(Calendar.MINUTE, -10);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        PendingIntent broadcast = PendingIntent.getBroadcast(this, Integer.parseInt(pillId), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("PILLSALARM","Pildora: "+pill.name+" Hora de la alarma: "+cal.getTime());

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    public void onResponseError() {
        Toast.makeText(this, "Se produjo un error de conexión en el pastillero, intente luego",
                Toast.LENGTH_LONG).show();
        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading);
        loadingView.setVisibility(View.INVISIBLE);
        finish();

    }


}
