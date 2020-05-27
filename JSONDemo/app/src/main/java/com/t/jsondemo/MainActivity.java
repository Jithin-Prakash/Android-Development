package com.t.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    public static class Downloader extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {

                url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                //urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1){

                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }
                return result;

            } catch (IOException e) {

                e.printStackTrace();

                return null;

            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Log.i("Website Content", s);

            System.out.println(s);

            try{

                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Name Content", weatherInfo);

                JSONArray jsonArray = new JSONArray(weatherInfo);

               for(int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    Log.i("main",jsonPart.getString("main"));
                    Log.i("description",jsonPart.getString("description"));

                }

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Downloader downloader = new Downloader();
        downloader.execute("http://samples.openweathermap.org/data/2.5/weather?q=London");

    }
}
