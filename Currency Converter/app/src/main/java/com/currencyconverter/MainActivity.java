package com.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void currencyConverter(View view){
        EditText editNumberText = (EditText) findViewById(R.id.editNumberText);
        double dollar = Double.parseDouble(editNumberText.getText().toString());
        double inr = dollar*75.38;
        Toast.makeText(MainActivity.this, "Rs"+ Double.toString(inr), Toast.LENGTH_LONG).show();
        Log.i("Amount", editNumberText.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
