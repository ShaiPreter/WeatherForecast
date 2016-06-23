package com.example.android.weatherforecast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shai Preter on 6/10/2016.
 */
public class WeatherParser {
    public static List<WeatherModel> ParseFeed(JSONObject content)  {


        try {
            JSONArray days = content.getJSONArray("list");
            List<WeatherModel> weatherlist = new ArrayList<>();

            for (int i = 0; i<days.length(); i++) {

                JSONObject day = days.getJSONObject(i);
                JSONArray details = day.getJSONArray("weather");
                JSONObject main = details.getJSONObject(0);
                JSONObject temp = day.getJSONObject("temp");
                WeatherModel WeatherModel = new WeatherModel();
                WeatherModel.setPosition(i);
                WeatherModel.setDate(day.getLong("dt"));
                WeatherModel.setDescription(main.getString("description"));
                WeatherModel.setTemperature(temp.getInt("day"));
                WeatherModel.setIcon(main.getString("icon"));

                weatherlist.add(WeatherModel);

            }
            Log.d("Confirmed", "JSON parsed");
            return weatherlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
