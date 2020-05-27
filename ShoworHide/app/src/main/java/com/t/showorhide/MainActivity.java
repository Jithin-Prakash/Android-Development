package com.t.showorhide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView text;
    //Button showText = (Button) findViewById(R.id.showButton);
    //Button hideText = (Button) findViewById(R.id.hideButton);


    public void show(View view){

        text.setAlpha(1.0f);

    }

    public void hide(View view){

        text.setAlpha(0.0f);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);
        //showText = (Button) findViewById(R.id.show);
        //hideText = (Button) findViewById(R.id.hide);

    }
}
