package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chefstable.models.Recipe;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

public class DetailedRecipeActivity extends AppCompatActivity {
    Button tryBtn;
    TextView recipeName, ingList, inst;
    ImageView foodPic; // image for food pulled from api



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        tryBtn = findViewById(R.id.try_button);
        recipeName = findViewById(R.id.receipe_name);
        ingList = findViewById(R.id.ingredient_list);
        inst = findViewById(R.id.instructions);

       Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("strMeal"));
        recipeName.setText(recipe.getTitle());
        inst.setText(recipe.getInstructions());

    //    Glide.with(conts).load(recipe.getMealThumb()).into(foodPic);



        tryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               goCompose();
            }
        });


    }

    private void goCompose() {
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("strMeal"));
        List<String> ins = instParcer(recipe.getInstructions());
       Intent c = new Intent(this, ComposeActivity.class);
       c.putExtra("instructs", testString(recipe.getInstructions()));
       // we need to add a putExtra in order to pass the name of the recipe onto the ComposeActivity!
        //display it in the textview and allow them to edit from there
       startActivity(c);

    }

    // hoping this works
    private String testString (String s){
        String [] testInstructs = s.split("[.]");
        String t = "";
        for (int i = 0; i < testInstructs.length; i++){
            t = "\n" + testInstructs[i];
        }
        return t;
    }

    // test
    private List<String> instParcer(String s) {
        String [] testInstructs = s.split("[.]");
        return Arrays.asList(testInstructs); // returns an array that will pass data without a period
    }
}