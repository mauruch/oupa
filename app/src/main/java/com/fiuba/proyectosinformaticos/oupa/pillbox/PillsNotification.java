package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.widget.Toast;


import com.fiuba.proyectosinformaticos.oupa.activities.MainActivity;

import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillClient;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillResponse;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PillsNotification implements PillClient {

    MainActivity mainActivity;
    private PillService pillService;
    private AlarmManager alarmManager;

    public PillsNotification(MainActivity mainActivity){

        this.mainActivity=mainActivity;
        this.alarmManager = (AlarmManager) mainActivity.getSystemService(Context.ALARM_SERVICE);
        this.pillService = new PillService();
    }

    public void scheduleNotifications(){

        pillService.getPillsForToday(this);

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

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
                Date parsedDate = dateFormat.parse(pillResponse.time);
                pill.date = parsedDate;

                if(!pill.drinked && Calendar.getInstance().getTime().before(pill.date)){
                    scheduleNotification(pill);
                }


            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
            }

        }
    }

    public void scheduleNotification(Pill pill) {
        Intent notificationIntent = new Intent(mainActivity, AlarmReceiver.class);

        notificationIntent.putExtra("pill.id",pill.id);
        /*notificationIntent.putExtra("pill.name",pill.name);
        notificationIntent.putExtra("pill.drinked",pill.drinked);
        notificationIntent.putExtra("pill.date",pill.date);*/

        notificationIntent.putExtra("pillForNotification",pill);

        PendingIntent broadcast = PendingIntent.getBroadcast(mainActivity, Integer.parseInt(pill.id), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        //cal.setTime(pill.date);
        //cal.add(Calendar.MINUTE, -10);
        int randomNum = 5 + (int)(Math.random() * ((20 - 5) + 1));
        cal.add(Calendar.SECOND,randomNum);

        Log.i("PILLSALARM","Pildora: "+pill.name+" Hora de la alarma: "+cal.getTime());

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    public void onResponseError() {
        Toast.makeText(mainActivity, "Se produjo un error de conexión en el pastillero, intente luego",
                Toast.LENGTH_LONG).show();

    }


}
