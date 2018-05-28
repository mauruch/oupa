package com.fiuba.proyectosinformaticos.oupa.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.CountDownTimer;
import com.fiuba.proyectosinformaticos.oupa.R;

public class SOSActivity extends AppCompatActivity {

    private TextView countDownText;
    private TextView titleText;
    private TextView infoText;
    private Button cancelSOSButton;
    private Button redirectButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        titleText = findViewById(R.id.titleText);
        infoText = findViewById(R.id.infoText);
        countDownText = findViewById(R.id.countDownText);
        cancelSOSButton = findViewById(R.id.cancelSOSButton);
        redirectButton = findViewById(R.id.redirectButton);

        countDownTimer = initCountDownTimer();
        countDownTimer.start();

        attachEvents();

    }

    private CountDownTimer initCountDownTimer() {

        return new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
                Long second = millisUntilFinished / 1000;
                countDownText.setText(second.toString());
            }

            public void onFinish() {
                titleText.setVisibility(View.INVISIBLE);
                countDownText.setVisibility(View.GONE);
                cancelSOSButton.setVisibility(View.GONE);

                infoText.setVisibility(View.VISIBLE);
                redirectButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void attachEvents() {

        cancelSOSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countDownTimer.pause();

                final AlertDialog alertDialog = new AlertDialog.Builder(SOSActivity.this)
                        .setMessage("¿Seguro que desea cancelar el pedido de ayuda?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                finish();
                                startActivity(mainActivity);
                            }

                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                countDownTimer.resume();
                            }
                        }).show();

                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                // override the text color of negative button
                negativeButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                negativeButton.setTextSize(25);

                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                // override the text color of positive button
                positiveButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                positiveButton.setTextSize(25);

                TextView dialogMessage = alertDialog.findViewById(android.R.id.message);
                dialogMessage.setTextSize(25);

            }
        });

        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(mainActivity);
            }
        });
    }
}
