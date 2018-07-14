package com.fiuba.proyectosinformaticos.oupa.measurement.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.measurement.model.Measurement;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.util.ArrayList;

public class MeasurementAdapter extends ArrayAdapter<Measurement> {

    public MeasurementAdapter(Context context, ArrayList<Measurement> measurements) {
        super(context, 0, measurements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Measurement measurement = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.measurement_view, parent, false);
        }

        TextView hourTextView = (TextView) convertView.findViewById(R.id.measurementTime);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.measurementTitle);
        TextView valueTextView = (TextView) convertView.findViewById(R.id.measuremenValue);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.measurementImage);

        hourTextView.setText(measurement.dateSring());
        valueTextView.setText(measurement.value);
        if(measurement.measurement_type.equals("temperature")){
            titleTextView.setText("Temperatura");
            imageView.setImageResource(R.drawable.temperature);
            convertView.setBackgroundColor(ContextCompat.getColor(super.getContext(),R.color.lightblue));

        }else if(measurement.measurement_type.equals("glucose")){
            titleTextView.setText("Glucosa");
            imageView.setImageResource(R.drawable.glucose);
            convertView.setBackgroundColor(ContextCompat.getColor(super.getContext(),R.color.midblue));

        }else{
            titleTextView.setText("Presión");
            imageView.setImageResource(R.drawable.preasure);
            convertView.setBackgroundColor(ContextCompat.getColor(super.getContext(),R.color.greenMaterial));

        }

        // Return the completed view to render on screen
        return convertView;
    }


}
