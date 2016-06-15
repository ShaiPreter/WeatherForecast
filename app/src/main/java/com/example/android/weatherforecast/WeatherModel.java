package com.example.android.weatherforecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Shai Preter on 6/4/2016.
 */
public class WeatherModel {
    private int temperature;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(long timestamp) {
        Date date = new Date(timestamp*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault());
        String formattedDate = sdf.format(date);
        this.date = formattedDate;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String description;
    private String date;
    private String icon;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;

}
