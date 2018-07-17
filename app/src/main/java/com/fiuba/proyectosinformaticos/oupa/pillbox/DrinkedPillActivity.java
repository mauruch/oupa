package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;
import com.fiuba.proyectosinformaticos.oupa.pillbox.services.PillService;

public class DrinkedPillActivity extends AppCompatActivity {

    private PillService pillService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drinked_pill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Pill pill = (Pill) getIntent().getSerializableExtra("pill");
        final Boolean comingFromNotification = (Boolean) getIntent().getBooleanExtra("comingFromNotification",false);

        String title = "Tomar " + pill.name;

        TextView hourTextView = (TextView) findViewById(R.id.pillTitle);
        hourTextView.setText(title);

        ImageButton confirmButton = (ImageButton) findViewById(R.id.btn_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkedPillActivity.this, PillboxActivity.class);
                pill.drinked = true;
                /*TODO Esto tuve que agregarlo porque no se ejecuta el onActivityResult de PillboxActivity cuando se invoca esta Activity desde la notificacion
                 */
                if(comingFromNotification)
                {
                    pillService = new PillService();
                    pillService.updatePillDrinked(getApplicationContext(),pill);
                }
                intent.putExtra("pill", pill);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

}
