package com.example.chefstable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;


public class editProfilePage extends AppCompatActivity {

        File photofile;
        String photofilename ="photo.jpg";
        private EditText name;
        private ImageView image_pro;
        private Button savePic_btn;
        Button tp;

        private String selectedPicture = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_edit_profile_page);

                tp = findViewById(R.id.take_pic);
                savePic_btn = findViewById(R.id.savePic_btn);
                image_pro = findViewById(R.id.image_pro);


                ParseUser user = ParseUser.getCurrentUser();
                ParseFile file = (ParseFile) user.get("profile");
                if(file != null){
                        Glide.with(getApplicationContext()).load(user.getParseFile("profile").getUrl()).into(image_pro);
                }

                tp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                               launchCamera();
                        }
                });

                savePic_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                savepost(photofile, user);
                        }
                });


        }

        private void savepost(File photofile, ParseUser user) {
                user.put("profile", new ParseFile(photofile));
                user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                                if(e != null){
                                        Log.e("here2", "here2");
                                        return;
                                }
                                ParseFile file = (ParseFile) user.get("profile");
                                if(file != null){
                                        Glide.with(getApplicationContext()).load(user.getParseFile("profile").getUrl()).into(image_pro);
                                }
                        }
                });
        }


        private void launchCamera() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photofile = getPhotoFileUri(photofilename);

                Uri fileProvider = FileProvider.getUriForFile(getApplicationContext(),"com.codepath.fileprovider", photofile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                someActivityResultLauncher.launch(intent);

        }

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                        Bitmap takenImage = BitmapFactory.decodeFile(photofile.getAbsolutePath());
                                        image_pro.setImageBitmap(takenImage);
                                }else{
                                        Toast.makeText(editProfilePage.this, "Picture not taken", Toast.LENGTH_SHORT).show();
                                }
                        }
                });


        private File getPhotoFileUri(String photofilename) {
                File mediaStorageDir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "editProfilePage");
                if(!mediaStorageDir.exists() && !mediaStorageDir.mkdir()){
                        Log.e("here", "here");
                }
                return new File(mediaStorageDir.getPath() + File.separator + photofilename);
        }

}


