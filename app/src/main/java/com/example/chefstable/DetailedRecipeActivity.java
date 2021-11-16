package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chefstable.models.Recipe;

import org.parceler.Parcels;

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
        foodPic = findViewById(R.id.ivFood);

       Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("strMeal"));
        recipeName.setText(recipe.getTitle());
        inst.setText(recipe.getInstructions());

        tryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ComposeFragment frag = new ComposeFragment();
               // getSupportFragmentManager().beginTransaction().replace(R.id.rl, frag, frag.getClass().getSimpleName()).addToBackStack(null).commit();
               goCompose();

            }
        });


    }

    private void goCompose() {
       Intent c = new Intent(this, ComposeActivity.class);
       // we need to add a putExtra in order to pass the name of the recipe onto the ComposeFragment!
        //display it in the textview and allow them to edit from there
       startActivity(c);

    }


}