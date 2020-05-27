package com.t.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int n;

    public void makeToast(String string){

        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void guess(View view){
        EditText guessEditText = (EditText) findViewById(R.id.guessEditText);
        int guessInt = Integer.parseInt(guessEditText.getText().toString());

        if (guessInt>n)
            makeToast("Lower!!");
        else if (guessInt<n)
            makeToast("Higher!!");
        else {

            makeToast("That's right!! \nTry again");
            Random rand = new Random();
            n = rand.nextInt(20)+1;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rand = new Random();
        n=rand.nextInt(20)+1;
    }
}
