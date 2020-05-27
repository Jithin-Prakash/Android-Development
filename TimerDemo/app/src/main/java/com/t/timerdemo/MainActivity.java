package com.t.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                Log.i("Runnable has run", "A second must have passed...");
                Toast.makeText(MainActivity.this, "Bro!!", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 1000);

            }
        };

        handler.post(run);*/
       Objects.requireNonNull(getSupportActionBar()).hide();

       new CountDownTimer(10000, 1000){

           public void onTick(long millisecondsUntilDone){

               Log.i("Seconds Left ",String.valueOf(millisecondsUntilDone /1000));

           }

           public  void onFinish(){

               Log.i("Done","Finished");

           }

       }.start();

    }
}
