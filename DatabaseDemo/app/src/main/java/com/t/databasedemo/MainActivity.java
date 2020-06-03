package com.t.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            SQLiteDatabase database = this.openOrCreateDatabase("Users", MODE_PRIVATE,null);

            database.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");

            database.execSQL("CREATE TABLE IF NOT EXISTS newUsers(name VARCHAR, age INTEGER(3), id INTEGER PRIMARY KEY)");

            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Jithin', 19)");

            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Prakash',20)");

            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Rob',21)");

            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Smith',22)");

            Cursor cursor = database.rawQuery("SELECT * FROM newUsers", null);

            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");
            int idIndex = cursor.getColumnIndex("id");

            cursor.moveToFirst();

            while (cursor != null){

                Log.i("name", cursor.getString(nameIndex));
                Log.i("age", Integer.toString(cursor.getInt(ageIndex)));
                Log.i("id", Integer.toString(cursor.getInt(idIndex)));

                cursor.moveToNext();

            }

        }catch(Exception e){

            e.printStackTrace();

        }
    }
}
