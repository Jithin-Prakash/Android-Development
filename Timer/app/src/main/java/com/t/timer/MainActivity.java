package com.t.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;
import java.lang.*;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    MediaPlayer mplayer;


    public void resetTimer(){

        countDownTimer.cancel();
        timerTextView.setText("00:00");
        timerSeekBar.setProgress(30);
        controllerButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
        if(mplayer.isPlaying())
            mplayer.stop();
        controllerButton.setAlpha(100f);



    }

    @SuppressLint("SetTextI18n")
    public void updateTimer(int secondsLeft){

        int minutes = (int)secondsLeft/60;
        int seconds = secondsLeft - minutes*60;
        @SuppressLint("DefaultLocale") String minute = String.format("%02d",minutes);
        @SuppressLint("DefaultLocale") String second = String.format("%02d",seconds);

        timerTextView.setText(minute+":"+second);

    }

    public void controlTimer(View view){

        if(!counterIsActive) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }


                @SuppressLint("SetTextI18n")
                public void onFinish() {

                    timerSeekBar.setEnabled(true);
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.batman_drunk);
                    mplayer.start();
                    if(mplayer.isPlaying() == true)
                        controllerButton.setAlpha(0f);

                    timerTextView.setText("00:00");

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetTimer();
                        }
                    }, 10000);


                    //resetTimer();

                }

            }.start();

        } else{

            //timerTextView.setText("00:00");
            countDownTimer.cancel();
            timerTextView.setText("00:00");
            controllerButton.setText("Go!");
            timerSeekBar.setEnabled(true);
            counterIsActive = false;




        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);


        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
