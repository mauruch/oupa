package com.fiuba.proyectosinformaticos.oupa.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;

public class PhoneActivity extends AppCompatActivity {

    private View.OnClickListener goHomeListener = goHomeListener();
    private View.OnClickListener callListener = callListener();
    private LinearLayout contacto1;
    private LinearLayout contacto2;
    private LinearLayout contacto3;
    private LinearLayout contacto4;
    private LinearLayout contacto5;
    private LinearLayout contacto6;
    private Button redirectButton;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        titleText = findViewById(R.id.titleText);
        redirectButton = findViewById(R.id.redirectButton);
        contacto1 = findViewById(R.id.contact1Layout);
        contacto2 = findViewById(R.id.contact2Layout);
        contacto3 = findViewById(R.id.contact3Layout);
        contacto4 = findViewById(R.id.contact4Layout);
        contacto5 = findViewById(R.id.contact5Layout);
        contacto6 = findViewById(R.id.contact6Layout);
        attachEvents();

    }


    private View.OnClickListener goHomeListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(mainActivity);
            }
        };
    }

    private View.OnClickListener callListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:541162676821"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        };
    }

    private void attachEvents() {
        redirectButton.setOnClickListener(goHomeListener);
        contacto1.setOnClickListener(callListener);
        contacto2.setOnClickListener(callListener);
        contacto3.setOnClickListener(callListener);
        contacto4.setOnClickListener(callListener);
        contacto5.setOnClickListener(callListener);
        contacto6.setOnClickListener(callListener);
    }

}
