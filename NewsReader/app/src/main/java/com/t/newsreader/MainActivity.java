package com.t.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    SQLiteDatabase articlesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);

       articlesDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);

       articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleId INTEGER, title VARCHAR, content VARCHAR)");

        listView.setAdapter(arrayAdapter);
        updateListView();

        DownloadTask task = new DownloadTask();

        try {

            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");

        }catch (Exception e){

            e.printStackTrace();

        }


    }

    public void updateListView(){

        Cursor cursor = articlesDB.rawQuery("SELECT * FROM articles ",null);

        int contentIndex = cursor.getColumnIndex("content");
        int titleIndex = cursor.getColumnIndex("title");

        if(cursor.moveToFirst()){

            titles.clear();
            content.clear();

            do {

                titles.add(cursor.getString(titleIndex));
                content.add(cursor.getString(contentIndex));

            } while (cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();

        }

    }

    public class DownloadTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            URL url;

            HttpURLConnection urlConnection = null;

            try {

                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                Log.i("URL Content", result);

                JSONArray jsonArray = new JSONArray(result);

                int numberOfItems = 20;

                if (jsonArray.length() < 20){

                    numberOfItems = jsonArray.length();

                }

                articlesDB.execSQL("DELETE FROM articles");

                for (int i = 0; i < numberOfItems; i++){

                    String articleId = jsonArray.getString(i);

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+ articleId + ".json?print=pretty");

                    urlConnection = (HttpURLConnection) url.openConnection();

                    inputStream = urlConnection.getInputStream();

                    reader = new InputStreamReader(inputStream);

                    data = reader.read();

                    String articleInfo = "";

                    while (data != -1){

                        char current = (char) data;

                        articleInfo += current;

                        data = reader.read();

                    }

                    JSONObject jsonObject = new JSONObject(articleInfo);

                    String articleTitle = jsonObject.getString("title");

                    String articleURL = jsonObject.getString("url");

                    url = new URL(articleURL);

                    urlConnection = (HttpURLConnection) url.openConnection();

                    inputStream = urlConnection.getInputStream();

                    reader = new InputStreamReader(inputStream);

                    data = reader.read();

                    String articleContent = "";

                    while (data != -1){

                        char current = (char) data;

                        articleContent += current;

                        data = reader.read();

                    }

                    Log.i("Article Content", articleContent);

                    String sql = "INSERT INTO articles( articleId, title, content) VALUES (?, ?, ?)";

                    SQLiteStatement statement = articlesDB.compileStatement(sql);

                    statement.bindString(1, articleId);
                    statement.bindString(2,articleTitle);
                    statement.bindString(3, articleContent);

                    statement.execute();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();


        }
    }
}
