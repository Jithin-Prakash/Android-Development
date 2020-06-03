package com.t.parsestarter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser.logOut();

        /*
        if (ParseUser.getCurrentUser() != null){

            Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());

        }

         */

        /*
        ParseUser.logInInBackground("jp", "123", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (user != null) {

                    Log.i("Login", "Successful");

                } else {

                    Log.i("Login", "Failed");

                }
            }
        });

         */

        /*
        ParseUser user = new ParseUser();

        user.setUsername("jp");
        user.setPassword("123");


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){

                    Log.i("SignUp", "Successful");

                }else
                    Log.i("SignUp", "Failed");

            }
        });

        */


        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

        query.whereEqualTo("userName", "Jithin");
        query.setLimit(1);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null){

                    Log.i("findInBackground", "Retrieved " + objects.size() + " objects");

                    if(objects.size() > 0){

                        for(ParseObject object : objects){

                            Log.i("findInBackgroundResult", object.getString("userName"));

                        }

                    }

                }

            }
        });

         */

        /*
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Score");
        parseQuery.getInBackground("oIdS7R3kyE", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e == null && object != null){

                    object.put("score", 100);
                    object.saveInBackground();

                    Log.i("Object Value", object.getString("userName"));
                    Log.i("Object value", Integer.toString(object.getInt("score")));

                }

            }
        });
         */

                ParseAnalytics.trackAppOpenedInBackground(getIntent());
        //StarterApplication starterApplication = new StarterApplication();
    }
}