package com.t.downloadingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImage;

    public void downloadImage(View view) throws ExecutionException, InterruptedException {

        ImageDownloader task = new ImageDownloader();
        try {

            Bitmap myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();

            downloadedImage.setImageBitmap(myImage);

        } catch (Exception e){

            e.printStackTrace();

        }

         //https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedImage = (ImageView) findViewById(R.id.imageView);
    }

    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return  myBitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

    }
}
