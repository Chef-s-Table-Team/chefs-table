package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

// Reference to Parstagram Sign-up activity.
public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private EditText etUsername;
    private EditText etPass;
    private EditText etEmail;
    private Button btnSign; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPass);
        etEmail = findViewById(R.id.etPass);
        btnSign  = findViewById(R.id.btnSign);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPass.getText().toString();
                String email = etEmail.getText().toString();
                ParseUser user = new ParseUser(); // here we will invoke a ParseUser obj

                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password);

                // In order to sign up, we must call the signUpInBackground -> to easily
                 //   fetch data from the back4app for us
                user.signUpInBackground(new SignUpCallback() {  // get user obj to sign up in background...
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Issue with registration", e);
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                        // else switch to the main activity
                    }
                });
            }
        });

    }
    // create a new intent when the registration has succeeded
    private void goMainActivity() {
        Intent m = new Intent(this, MainActivity.class);
        startActivity(m);
        finish();
    }
}