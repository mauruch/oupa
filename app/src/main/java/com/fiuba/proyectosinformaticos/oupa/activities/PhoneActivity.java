package com.fiuba.proyectosinformaticos.oupa.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;

public class PhoneActivity extends AppCompatActivity {

    private View.OnClickListener callListener = callListenerGeneric();

    private View.OnClickListener callListenerAgus = callListenerAgus();
    private View.OnClickListener callListenerAna = callListenerAna();

    private View.OnClickListener callListenerOsiris = callListenerOsiris();

    private View.OnClickListener callListenerMau = callListenerMau();
    private View.OnClickListener callListenerNere = callListenerNere();
    private LinearLayout contacto1;
    private LinearLayout contacto2;
    private LinearLayout contacto3;
    private LinearLayout contacto4;
    private LinearLayout contacto5;
    private LinearLayout contacto6;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();



        setContentView(R.layout.activity_phone);

        titleText = findViewById(R.id.titleText);
        contacto1 = findViewById(R.id.contact1Layout);
        contacto2 = findViewById(R.id.contact2Layout);
        contacto3 = findViewById(R.id.contact3Layout);
        contacto4 = findViewById(R.id.contact4Layout);
        contacto5 = findViewById(R.id.contact5Layout);
        contacto6 = findViewById(R.id.contact6Layout);
        attachEvents();

    }



    public void callPhoneNumber(String phoneNumber)
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(PhoneActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }





    private View.OnClickListener callListenerGeneric() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber("+5491162676821");

            }
        };
    }

    private View.OnClickListener callListenerAgus() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber("+5491164109956");

            }
        };
    }

    private View.OnClickListener callListenerNere() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber("+5491165287206");

            }
        };
    }
    private View.OnClickListener callListenerMau() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber("+5491144103879");

            }
        };
    }
    private View.OnClickListener callListenerOsiris() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber("+5491169691577");

            }
        };
    }
    private View.OnClickListener callListenerAna() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber("+5491164756705");

            }
        };
    }


    private void attachEvents() {
        contacto1.setOnClickListener(callListenerOsiris);
        contacto2.setOnClickListener(callListenerNere);
        contacto3.setOnClickListener(callListenerMau);
        contacto4.setOnClickListener(callListenerMau);
        contacto5.setOnClickListener(callListenerMau);
        contacto6.setOnClickListener(callListenerAna);
    }

}
