package com.fiuba.proyectosinformaticos.oupa.pillbox.views;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.pillbox.model.Pill;

import java.util.ArrayList;


public class PillAdapter extends ArrayAdapter<Pill> {

    public PillAdapter(Context context, ArrayList<Pill> pills) {
        super(context, 0, pills);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Pill pill = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pill_view, parent, false);
        }
        // Lookup view for data population

        TextView hourTextView = (TextView) convertView.findViewById(R.id.pillTime);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.pillTitle);

        // Populate the data into the template view using the data object
        hourTextView.setText(pill.hourString());
        nameTextView.setText(pill.name);

        //TODO:segun la hora (hacerlo en el model)
        convertView.setBackgroundColor(Color.BLUE);

        // Return the completed view to render on screen
        return convertView;
    }

}
