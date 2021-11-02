package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private EditText etUsername;

    private EditText etPassword;

    private Button login_button;

    private Button sign_up_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if (ParseUser.getCurrentUser() != null) {


                go_to_main_activity();

        }

            etUsername = findViewById(R.id.etUsername);

            etPassword = findViewById(R.id.etPassword);

            login_button = findViewById(R.id.login_button);

            sign_up_button = findViewById(R.id.sign_up_button);

            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //log in message for error logging

                    Log.i(TAG, "onClick login");

                    //get text from username + password fields, and login user

                    String user = etUsername.getText().toString();
                    String password = etPassword.getText().toString();


                    log_in_user(user, password);

                }
            });

            sign_up_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "onClick sign up button");

                    //get text from username + password fields, and sign up user

                    Intent b = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(b);

                }
            });


        }
        /*
        private void create_user (String new_user, String password){

            //create parse user

<<<<<<<<< Temporary merge branch 1
    /*
    private void create_user(String new_user, String password) {

        //create parse user

            ParseUser user = new ParseUser(); //parse app code to be written

            //set user + password for User class instance
>>>>>>>>> Temporary merge branch 2

            user.setUsername(new_user);

            user.setPassword(password);

            //create sign up to be done in background

            user.signUpInBackground(new SignUpCallback() {

                public void done(ParseException e) {

                    if (e == null) { //log in valid?

                        go_to_main_activity();

                        Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                        //let user use app afterwards :)

                    } else { //issue with sign up

                        Log.e(TAG, "Sign up failed, error: ", e);

                        Toast.makeText(LoginActivity.this, "Sign up issue.", Toast.LENGTH_SHORT).show();

                        return;

                    }

                }

            });

    }
*/
    private void log_in_user (String user, String password){

            Log.i(TAG, "Attempting to log in user: " + user + "...");

            //user signed in properly? go to main activity

            ParseUser.logInInBackground(user, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e != null) {

                        Log.e(TAG, "Log in issue: ", e);

                        Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT);

                        return;

                    }

                    go_to_main_activity();

                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                }

            });

    }





        private void go_to_main_activity () {

            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);
            finish();

        }

    }
