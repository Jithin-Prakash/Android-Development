package com.t.jsondemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Downloader downloader = new Downloader();

        downloader.execute("http://api.weatherapi.com/v1/current.json?key=fb18562f215245dd95245958202605&q=India");
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

                JSONObject condition = currentJsonObject.getJSONObject("condition");

                //Log.i("Condition", condition.toString());

                String text = condition.getString("text");

                Log.i("Text", text);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
    }


}
