package com.example.android.weatherforecast;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends ListActivity {

    String mainUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    String accessCode = "&units=metric&APPID=4907e373098f091c293b36b92d8f0886";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText getcityview = (EditText)findViewById(R.id.City);
        Button searchButton = (Button) findViewById(R.id.search);
        searchButton.setOnClickListener(search);

    }

    public String getcity(){

        EditText getcityview = (EditText)findViewById(R.id.City);
        String Cityname = getcityview.getText().toString().trim();
        String result = deleteSpace(Cityname);
        return result;

    }

    public String deleteSpace(String city){
       int loc = city.indexOf(" ");
        if (loc != -1){
            String result = city.substring(0, loc) + city.substring(loc + 1);
            return result;
        }
        else {
            return city;
        }
    }

    public void showForecast(){
        EditText getcityview = (EditText)findViewById(R.id.City);
        if (!isEmpty(getcityview) ) {
            Log.d("Status:", "running");
           String cityName = getcity();
            String url = mainUrl + cityName + accessCode;
            JsonParser Jparser = new JsonParser();
            Jparser.execute(url);

        }
        else {
            Toast.makeText(getApplicationContext(), "No City entered", Toast.LENGTH_SHORT);
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    public View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showForecast();
        }
    };
    public void UpdateDisplay(List<WeatherModel> weatherlist){
        WeatherAdapter weatheradapter = new WeatherAdapter(this, R.layout.weather_item, weatherlist);
        setListAdapter(weatheradapter);
    }


    private class JsonParser extends  AsyncTask<String, String, List<WeatherModel>>{
        final String TAG = "JsonParser.java";

        InputStream is = null;
        JSONObject jObj = null;
        String json = "";
        HttpURLConnection UrlConnection = null;
        List<WeatherModel> weatherList;

        // make HTTP request


        @Override
        protected List<WeatherModel> doInBackground(String...params) {
            try {

                URL Url = new URL(params[0]);
                UrlConnection = (HttpURLConnection) Url.openConnection();
                UrlConnection.connect();

                is = UrlConnection.getInputStream();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();

            } catch (Exception e) {
                Log.e(TAG, "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing data " + e.toString());
            }

            // return JSON String
            Log.d("Confirmed", "Got JSON");
            weatherList = WeatherParser.ParseFeed(jObj);
            for (WeatherModel WeatherModel: weatherList )
            try {
                String Base_Url = "http://openweathermap.org/img/w/";
                String icon_Url = Base_Url + WeatherModel.getIcon() + ".png";
                Log.d("URL:", icon_Url);
                InputStream in = new URL(icon_Url).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                WeatherModel.setImage(bitmap);
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return weatherList;
        }


        @Override
        protected void onPostExecute(List<WeatherModel> o) {
            super.onPostExecute(o);

            UpdateDisplay(weatherList);



        }


    }

}


