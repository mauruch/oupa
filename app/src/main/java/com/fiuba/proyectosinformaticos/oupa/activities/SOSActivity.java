package com.fiuba.proyectosinformaticos.oupa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.services.SOSAlertService;

public class SOSActivity extends AppCompatActivity {

    private TextView countDownText;
    private TextView titleText;
    private TextView infoText;
    private Button cancelSOSButton;
    private Button redirectButton;
    private CountDownTimer countDownTimer;
    private View.OnClickListener goHomeListener = goHomeListener();

    private SOSAlertService alertService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertService = new SOSAlertService();

        setContentView(R.layout.activity_sos);

        getSupportActionBar().hide();

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

                //send alert against server.
                alertService.sendSOSAlert();
            }
        };
    }

    private void attachEvents() {
        cancelSOSButton.setOnClickListener(goHomeListener);
        redirectButton.setOnClickListener(goHomeListener);
    }

    private View.OnClickListener goHomeListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(mainActivity);
            }
        };
    }
}
