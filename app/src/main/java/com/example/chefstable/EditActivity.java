package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chefstable.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditActivity extends AppCompatActivity {
    private Button btnEdButton, btnPic;
    private EditText editText;
    private TextView tvCount;
    private ImageView ivProfPic;
    public static final int MAX_CHARS = 150;
    Post post;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        btnEdButton = findViewById(R.id.btnSave);
        editText = findViewById(R.id.tvEdBio);
        tvCount = findViewById(R.id.tvCount);
        btnPic = findViewById(R.id.btnPic);
        ivProfPic = findViewById(R.id.image_pro);

        ParseUser user = ParseUser.getCurrentUser();
        ParseFile profPic = ParseUser.getCurrentUser().getParseFile("profile"); // retrieving the profile
        post = new Post();
        post.setUser(user); // sets the current user
        editText.setText(ParseUser.getCurrentUser().get("bio").toString());
        if (profPic != null) {
            Glide.with(this).load(post.getProfilePicture().getUrl()).into(ivProfPic);
        }

        String bio = editText.getText().toString();
        if (bio.isEmpty()) {
            Toast.makeText(EditActivity.this, "Your bio cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if (bio.length() > MAX_CHARS) {
            Toast.makeText(EditActivity.this, "Your bio is greater than 150 characters", Toast.LENGTH_SHORT).show();
        }

        btnEdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedBio = editText.getText().toString();
               // ParseUser user1 = ParseUser.getCurrentUser();
                user.put("bio", updatedBio);

                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("Here", "here");
                        }
                        Intent m = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(m);
                        finish();
                    }
                });
            }
        });


        // creating a test watcher
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            int charCount = MAX_CHARS - editText.length();
            String convert = String.valueOf(charCount);
            if (charCount == 0 ){
                btnEdButton.setEnabled(false);
            }
            tvCount.setText(convert);
            }
        });
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this,editProfilePage.class);
                startActivity(intent);
            }
        });
    }
}