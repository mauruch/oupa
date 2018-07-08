package com.fiuba.proyectosinformaticos.oupa.pillbox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.OUPADateFormat;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;


public class NewPillStep2 extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    public static final int STEP_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill_step2);

        updateDate();
    }


    private void updateDate() {
        OUPADateFormat customDateFormat = new OUPADateFormat();
        DateFormat hourFormat = new SimpleDateFormat(customDateFormat.timeFormatForServer());
        TextView pillHourText = findViewById(R.id.pill_hour_info);
        String hours = hourFormat.format(myCalendar.getTime());
        pillHourText.setText("Hora: " + hours);

        DateFormat dateFormat = new SimpleDateFormat(customDateFormat.dateFormatPill());
        TextView pillDateText = findViewById(R.id.pill_date_info);
        String date = dateFormat.format(myCalendar.getTime());
        pillDateText.setText("Fecha: " + date);
    }

    public void selectDateButtonPressed(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewPillStep2.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    public void selectTimeButtonPressed(View view) {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                myCalendar.set(Calendar.AM_PM, Calendar.AM);
                updateDate();
            }
        },  Calendar.HOUR_OF_DAY,  Calendar.MINUTE, false);

        recogerHora.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }

    };

    public void confirmButtonPressed(View view) {
        final Pill pill = (Pill) getIntent().getSerializableExtra("pill");
        pill.date = myCalendar.getTime();

        //TODO: step de repetir pasti
        Intent intent = new Intent(NewPillStep2.this, NewPillStep4.class);
        intent.putExtra("pill", pill);
        intent.addFlags(FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }

}
