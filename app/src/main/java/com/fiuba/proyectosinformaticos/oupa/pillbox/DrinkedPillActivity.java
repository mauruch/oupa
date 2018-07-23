package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;

import org.w3c.dom.Text;

public class DrinkedPillActivity extends AppCompatActivity {

    private PillService pillService;
    private Pill pill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drinked_pill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pill = (Pill) getIntent().getSerializableExtra("pill");
        final Boolean comingFromNotification = (Boolean) getIntent().getBooleanExtra("comingFromNotification",false);

        String title = "Tomar " + pill.name;

        TextView hourTextView = (TextView) findViewById(R.id.pillTitle);
        hourTextView.setText(title);

        final ImageButton confirmButton = (ImageButton) findViewById(R.id.btn_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pill.drinked = true;
                pillService = new PillService();
                pillService.updatePillDrinked(getApplicationContext(),pill,DrinkedPillActivity.this);

                v.setVisibility(View.INVISIBLE);
                TextView confirmText = (TextView) findViewById(R.id.confirm);
                confirmText.setVisibility(View.INVISIBLE);

                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loadingDrinkedPill);
                loadingView.setVisibility(View.VISIBLE);

            }
        });


    }

    public void onResponseSuccess(){
        Intent intent = new Intent(DrinkedPillActivity.this, PillboxActivity.class);
        intent.putExtra("pill", pill);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onResponseError() {
        Toast.makeText(this, "Se produjo un error de conexión al confirmar la pastilla, intente nuevamente",
                Toast.LENGTH_LONG).show();

        ImageButton confirmButton = (ImageButton) findViewById(R.id.btn_confirm);
        confirmButton.setVisibility(View.VISIBLE);
        TextView confirmText = (TextView) findViewById(R.id.confirm);
        confirmText.setVisibility(View.VISIBLE);

        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loadingDrinkedPill);
        loadingView.setVisibility(View.INVISIBLE);
    }
}
