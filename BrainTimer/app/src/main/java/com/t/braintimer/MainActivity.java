package com.t.braintimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {





    Button startButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int noOfQuestions=0;

    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;
    ConstraintLayout gameConstrainLayout;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;


    public void playAgain(View view){

        score = 0;
        noOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        generateQuestion();

        playAgainButton.setVisibility(View.INVISIBLE);

        new CountDownTimer(30100, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");

            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: " + Integer.toString(score) + " / " + Integer.toString(noOfQuestions));
                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);

            }
        }.start();


    }

    public void generateQuestion(){

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int incorrectAnswer;
        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        sumTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));

        for(int i = 0; i < 4; i++){

            if(i == locationOfCorrectAnswer){

                answers.add(a+b);

            } else {

                incorrectAnswer = rand.nextInt(41);
                while(incorrectAnswer == a+b){

                    incorrectAnswer = rand.nextInt(41);

                }
                answers.add(incorrectAnswer);

            }

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    @SuppressLint("SetTextI18n")
    public void chooseAnswer(View view){

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

            Log.i("correct","correct");
            resultTextView.setText("Correct!");
            score++;

        }
        else
            resultTextView.setText("Wrong!");

        noOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + " / " + Integer.toString(noOfQuestions));
        generateQuestion();

    }

    public void start(View view){

        startButton.setVisibility(View.INVISIBLE);
        gameConstrainLayout.setVisibility(View.VISIBLE);

       /* pointsTextView.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
       gridLayout.setVisibility(View.VISIBLE);*/

        playAgain(findViewById(R.id.playAgainButton));







    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        startButton = (Button) findViewById(R.id.startButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        gameConstrainLayout = (ConstraintLayout) findViewById(R.id.gameConstraintLayout);



        button0 = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);





    }
}
