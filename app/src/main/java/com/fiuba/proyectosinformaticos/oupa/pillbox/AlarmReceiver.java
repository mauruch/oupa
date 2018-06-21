package com.fiuba.proyectosinformaticos.oupa.pillbox;


import android.app.PendingIntent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.util.Date;


public class AlarmReceiver extends BroadcastReceiver{
    public static final String CHANNEL_ID = "com.fiuba.proyectosinformaticos.oupa.pillbox.notificationchannel";

    @Override
    public void onReceive(Context context, Intent intent) {

        int idPastilla = intent.getIntExtra("pill.id",0);
        Log.i("Pastillas",""+idPastilla);

        /**TODO Por alguna razon no pude mandar un objeto Pill*/
        Pill pill = new Pill();
        pill.id=intent.getIntExtra("pill.id",0);
        pill.name=intent.getStringExtra("pill.name");
        pill.drinked=intent.getBooleanExtra("pill.drinked",false);
        pill.date=(Date) intent.getSerializableExtra("pill.date");

        // Create an explicit intent for an Activity in your app
        Intent pillboxIntent = new Intent(context, DrinkedPillActivity.class);
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addNextIntentWithParentStack(pillboxIntent);

        pillboxIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pillboxIntent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.pill_notification);
        mBuilder.setContentTitle(context.getResources().getString(R.string.pill_notification_content_title));
        mBuilder.setContentText(context.getResources().getString(R.string.pill_notification_content_text));
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mBuilder.setVibrate(new long[] { 1000, 1000});

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(2, mBuilder.build());

    }
}
