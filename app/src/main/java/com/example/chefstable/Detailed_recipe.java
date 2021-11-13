package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chefstable.models.Recipe;

import org.parceler.Parcels;

public class Detailed_recipe extends AppCompatActivity {
    Button tryBtn;
    TextView recipeName, ingList, inst;
    ImageView foodPic; // image for food pulled from api
  //  public static final String RECIPE_KEY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        tryBtn = findViewById(R.id.try_button);
        recipeName = findViewById(R.id.receipe_name);
        ingList = findViewById(R.id.ingredient_list);
        inst = findViewById(R.id.instructions);
        foodPic = findViewById(R.id.ivFood);

       Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("idMeal"));
        recipeName.setText(recipe.getTitle());
        inst.setText(recipe.getInstructions());


    }



}