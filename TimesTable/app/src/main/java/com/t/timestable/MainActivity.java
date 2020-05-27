package com.t.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.StringSearch;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    ListView listView;

    public void generateTimesTable(int timesTable){

        List<String> timesTableContent = new ArrayList<String>();

        for(int i = 1; i <= 10; i++){
            timesTableContent.add(Integer.toString(i * timesTable));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timesTableContent);

        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        listView = (ListView) findViewById(R.id.listView);

        seekBar.setMax(20);
        seekBar.setProgress(10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int min = 1;
                int timesTable;
                if (progress<min){

                    timesTable = min;
                    seekBar.setProgress(min);

                }
                else
                    timesTable = progress;

                generateTimesTable(timesTable);

                Toast.makeText(MainActivity.this, "Times Table of " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateTimesTable(10);

    }
}
