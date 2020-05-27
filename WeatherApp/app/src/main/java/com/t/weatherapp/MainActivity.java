package com.t.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView resultTextView, textView;

    public void findWeather(View view){

        Log.i("cityName", cityName.getText().toString());

        Downloader downloader = new Downloader();
        downloader.execute("http://api.weatherapi.com/v1/current.json?key=fb18562f215245dd95245958202605&q=" + cityName.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        textView = (TextView) findViewById(R.id.textView);

    }

    public class Downloader extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";

            URL url;

            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                ///Log.i("Full", jsonObject.toString());

                //String current = jsonObject.getString("current");

                //Log.i("Current", current);

                JSONObject currentJsonObject = jsonObject.getJSONObject("current");

                String temperature = currentJsonObject.getString("temp_c");

                Log.i("Temperature", temperature);

                resultTextView.setText("Temperature: " + temperature + " C ");

                JSONObject condition = currentJsonObject.getJSONObject("condition");

                //Log.i("Condition", condition.toString());

                String text = condition.getString("text");

                Log.i("Text", text);

                textView.setText(" Condition: "+  text);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
    }

}
