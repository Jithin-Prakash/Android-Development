package com.t.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    List<String> countryURLs = new ArrayList<String>();
    List<String> flagURLs = new ArrayList<String>();
    int chosenCountry;
    ImageView imageView;
    int locationOfCorrectAnswer;
    String[] answers = new String[4];

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    public void generator(){

        Downloader task = new Downloader();

        String result = null;

        try {
            result = task.execute("https://www.countries-ofthe-world.com/flags-of-the-world.html").get();

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(result);

            while (m.find()){

                countryURLs.add("https://www.countries-ofthe-world.com/"+m.group(1));
            }

            //Log.i("No of flags: " ,String.valueOf(countryURLs.size()));

            Pattern p1 = Pattern.compile("alt=\"(.*?)\"");
            Matcher m1 =p1.matcher(result);

            while (m1.find())
            {

                flagURLs.add(m1.group(1));


            }

            //Log.i("No of countries: " ,String.valueOf(flagURLs.size()));

            Random random = new Random();
            chosenCountry = random.nextInt(countryURLs.size());

            ImageDownloader imageDownloader = new ImageDownloader();

            Bitmap flagImage;

            flagImage = imageDownloader.execute(countryURLs.get(chosenCountry)).get();

            imageView.setImageBitmap(flagImage);

            locationOfCorrectAnswer = random.nextInt(4);

            int incorrectAnswerLocation;

            for (int i =0; i < 4; i++){

                if (i == locationOfCorrectAnswer){

                    answers[i] = flagURLs.get(chosenCountry);

                } else {

                    incorrectAnswerLocation =random.nextInt(countryURLs.size());

                    while(incorrectAnswerLocation == chosenCountry) {

                        incorrectAnswerLocation =random.nextInt(countryURLs.size());

                    }

                    answers[i] = flagURLs.get(incorrectAnswerLocation);

                }

            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("Range")
    public void countryChosen(View view){

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

           Toast toast = Toast.makeText(this, "  Correct!!  ", Toast.LENGTH_SHORT);
           toast.show();

        }

        else
            Toast.makeText(this, "Absolutely Wrong, Correct Answer is "+ flagURLs.get(chosenCountry), Toast.LENGTH_SHORT).show();

        generator();

    }

    public static class ImageDownloader extends AsyncTask<String,Void, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;

            } catch (IOException e) {

                e.printStackTrace();

            }

            return  null;

        }
    }

    public static class Downloader extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {

            StringBuilder result = new StringBuilder();

            URL url = null;

            HttpsURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpsURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();

                while(data != -1){

                    char current = (char) data;

                    result.append(current);

                    data = reader.read();

                }

                return result.toString();

            } catch (Exception e){

                e.printStackTrace();

                return "Fail";
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Objects.requireNonNull(getSupportActionBar()).hide();

        imageView = (ImageView) findViewById(R.id.imageView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        generator();





    }
}
