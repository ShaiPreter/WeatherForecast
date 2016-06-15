package com.example.android.weatherforecast;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by Shai Preter on 6/13/2016.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherModel> {

    private Context context;
    private List<WeatherModel> weatherlist;
    public WeatherAdapter(Context context, int resource, List<WeatherModel> objects){
        super(context, resource, objects);
        this.context = context;

        this.weatherlist = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.weather_item, parent, false);

        WeatherModel weather = weatherlist.get(position);
        TextView day = (TextView) view.findViewById(R.id.day);
        TextView temperature = (TextView) view.findViewById(R.id.temperature);
        TextView description = (TextView) view.findViewById(R.id.description);
        String Temperature = Integer.toString(weather.getTemperature()) + "°C";
        Log.d("Temperature:", Temperature);
        Log.d("Temperature:", weather.getDescription());

        day.setText(weather.getDate());
        temperature.setText(Temperature);
        description.setText(weather.getDescription());
        return view;


    }

}
