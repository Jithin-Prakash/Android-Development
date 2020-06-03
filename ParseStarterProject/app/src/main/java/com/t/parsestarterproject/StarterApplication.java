package com.t.parsestarterproject;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("myappId")
            .clientKey("mwuPhNv8grPv")
            .server("http://3.7.248.123/parse/")
            .build()
        );

        ParseObject object = new ParseObject("Example Object");
        object.put("myNumber", "123");
        object.put("myString", "Jithin");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

                    Log.i("Parse result", "Successful!");

                }else{

                    Log.i("Parse result", "Failed");

                }
            }
        });

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
