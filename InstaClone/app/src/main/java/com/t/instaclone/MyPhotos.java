package com.t.instaclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MyPhotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);

        Intent intent = getIntent();

        String username = intent.getStringExtra("username");

        setTitle("My Feed");

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", username);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null && objects.size() > 0){

                    for (final ParseObject object : objects){

                        ParseFile file = (ParseFile) object.get("image");

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(e == null){

                                    if(data != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0, data.length);

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        imageView.setImageBitmap(bitmap);

                                        linearLayout.addView(imageView);

                                       /* imageView.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View v) {

                                                new AlertDialog.Builder(getApplicationContext())
                                                        .setIcon(android.R.drawable.alert_dark_frame)
                                                        .setTitle("Delete")
                                                        .setMessage("Are You Sure?")
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                object.deleteInBackground(new DeleteCallback() {
                                                                    @Override
                                                                    public void done(ParseException e) {

                                                                        if (e == null){

                                                                            Toast.makeText(MyPhotos.this, "Image deleted\n Restart app to see changes.", Toast.LENGTH_SHORT).show();

                                                                        }

                                                                    }
                                                                });

                                                            }
                                                        })
                                                        .setNegativeButton("No", null)
                                                        .show();


                                                return true;
                                            }
                                        });

                                        */

                                    }

                                }

                            }
                        });

                    }

                }else{

                    Toast.makeText(MyPhotos.this, "You haven't uploaded any photos.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}