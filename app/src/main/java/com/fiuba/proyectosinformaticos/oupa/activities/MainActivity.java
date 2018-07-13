package com.fiuba.proyectosinformaticos.oupa.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.Flashlight;
import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.contacts.ContactActivity;
import com.fiuba.proyectosinformaticos.oupa.pillbox.AlarmReceiver;
import com.fiuba.proyectosinformaticos.oupa.pillbox.PillboxActivity;
import com.fiuba.proyectosinformaticos.oupa.pillbox.PillsNotification;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userSessionManager = new UserSessionManager(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set hour
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
        TextView hour = findViewById(R.id.hour);
        hour.setText(formatDate.format(new Date()));

        //set date
        TextView date = findViewById(R.id.date);
        DateFormat df = new DateFormat();
        date.setText(df.format("dd/MM", new java.util.Date()));

        //set day of week
        formatDate = new SimpleDateFormat("EEE");
        TextView dayWeek = findViewById(R.id.day_week);
        dayWeek.setText(formatDate.format(new Date()));

        createNotificationChannel();

        attachEvents();

        Log.i(getClass().getCanonicalName(), "Firebase token: " + FirebaseInstanceId.getInstance().getToken());

        setUpPillsNotification();

    }

    private void setUpPillsNotification() {

        final PillsNotification pillsNotification = new PillsNotification(this);

        pillsNotification.scheduleNotifications();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(AlarmReceiver.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void attachEvents() {

        LinearLayout cameraLayout = findViewById(R.id.camera_layout);
        cameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        LinearLayout galeryLayout = findViewById(R.id.gallery_layout);
        galeryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
            }
        });


        LinearLayout sosLayout = findViewById(R.id.sos_layout);
        sosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sosActivity = new Intent(getApplicationContext(), SOSActivity.class);
                finish();
                startActivity(sosActivity);
            }
        });

        LinearLayout phoneLayout = findViewById(R.id.phone_layout);
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneActivity = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(phoneActivity);
            }
        });


        LinearLayout pillLayout = findViewById(R.id.pills_layout);
        pillLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pillboxIntent = new Intent(MainActivity.this, PillboxActivity.class);
                MainActivity.this.startActivity(pillboxIntent);
            }
        });

        final LinearLayout flashlightLayout = findViewById(R.id.flashlight_layout);
        flashlightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flashlight flashlight = Flashlight.getInstance();
                flashlight.toggleFlashlight(MainActivity.this);

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menu.getItem(0).setTitle(userSessionManager.getFullName());
        menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.close));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            userSessionManager.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}