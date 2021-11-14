package com.example.chefstable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseUser;

public class Detailed_recipe extends AppCompatActivity {
    private Button try_button;
    private EditText recipe_name;
    private EditText ingredient_list;
    private EditText instructions;
    private ImageView foodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe_name = view.findViewById(R.id.recipe_name);
        ingredient_list = view.findViewById(R.id.ingredient_list);
        instructions = view.findViewById(R.id.instructions);
        foodImage = view.findViewById(R.id.foodImage);
        try_button = view.findViewById(R.id.try_button);
        try_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String ingredient_list = ingredient_list.getText().toString();
            if(ingredient_list.isEmpty()){
                Toast.makeText(getContext(), "ingredient list cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
//            ParseUser currentUser = ParseUser.getCurrentUser();
//            savePost(description, currentUser, photoFile);
        }
    });

    private void gotoDetailedPage(){
        Intent i = new Intent(gotoDetailedPage(), gotoDetailedPage.class);
        startActivity(i);
        gotoDetailedPage().finish();
    }



    private Activity gotoDetailedPage() {
        return null;
    }