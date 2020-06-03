package com.t.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameEditText;
    EditText passwordEditText;

    public void showUserList(){

        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);

    }

    public void logIn(View view){

       if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

           Toast.makeText(this, "A username and password is required.", Toast.LENGTH_SHORT).show();

       }else {

           ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
               @Override
               public void done(ParseUser user, ParseException e) {

                   if (user != null) {

                       Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                       showUserList();

                   } else {

                       Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                   }

               }
           });

       }

    }


    public void signUp(View view){

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password is required.", Toast.LENGTH_SHORT).show();
            
        }else{

            ParseUser user = new ParseUser();

            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if(e == null){

                        Log.i("Sign Up", "Successful");
                        showUserList();

                    }else{

                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constrainLayout);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        constraintLayout.setOnClickListener(this);
        imageView.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){

            showUserList();

        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public void onClick(View v) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

}