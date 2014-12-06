package com.example.lovehate.loveandhate;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    // construct to our MyAdapter
    public MyAdapter(Context context, int resource, int textViewResourceId,
                     String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.values = objects;
    }

    // Method to put every item in the fragment
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // We said the layout that we want to get the icons and label
            rowView = inflater.inflate(R.layout.listview_custom, parent, false);
        } else {
            rowView = convertView;
        }

        TextView textToShow = (TextView) rowView.findViewById(R.id.label);
        ImageView iconToShow = (ImageView) rowView.findViewById(R.id.icon);
        textToShow.setText(values[position]);
        //To some label we add a icon depends the words
        String s = values[position];

        if (s.startsWith("Win")) {
            iconToShow.setImageResource(R.drawable.windows);
        } else if (s.startsWith("I") || s.startsWith("Mac")) {
            iconToShow.setImageResource(R.drawable.mac);
        } else if (s.startsWith("Ubuntu") || s.startsWith("Lin")) {
            iconToShow.setImageResource(R.drawable.linux);
        } else if (s.startsWith("Black")) {
            iconToShow.setImageResource(R.drawable.blackberry);
        }
        else {
            iconToShow.setImageResource(R.drawable.android);
        }
        return rowView;
    }
}