package com.fiuba.proyectosinformaticos.oupa.measurement.heartRate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;

public class HeartRateStartActivity extends AppCompatActivity {

    private Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_start);

        measurement = (Measurement) getIntent().getSerializableExtra("measurement");

        Button startHeartRateMonitor = (Button) findViewById(R.id.confirmButtonStartHeartRate);
        startHeartRateMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HeartRateStartActivity.this, HeartRateMonitor.class);
                intent.putExtra("measurement", measurement);
                intent.addFlags(FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });

    }
}
