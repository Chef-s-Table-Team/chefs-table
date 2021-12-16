package com.example.chefstable.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    String mealThumb;
    String title, instructions,category, area, ingredients_;
    String ingredients [] = new String [20];
    String measurements [] = new String [20];

    public Recipe () {}

    public Recipe(JSONObject jsonObject) throws JSONException {
        mealThumb=jsonObject.getString("strMealThumb");
        title=jsonObject.getString("strMeal");
        instructions = jsonObject.getString("strInstructions");
        category = jsonObject.getString("strCategory");
        area = jsonObject.getString("strArea");


        // loop through the array and grab ingredients / measurements
        for (int i = 1; i < ingredients.length; i++) {
            if (ingredients[i] == " " || ingredients[i] == "null" || ingredients[i] == "") {
                break;
            }
            ingredients[i] = "-" + jsonObject.getString("strIngredient"+i) + " " + ":" + jsonObject.getString("strMeasure"+i); // grabbing each ingredient
        }
       ingredients_ = ingredientsToString(ingredients);

    }

    public static List<Recipe> fromJsonArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes=new ArrayList<>();
        for(int i=0; i < recipeJsonArray.length(); i++) {
            recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }
    public String ingredientsToString (String [] ingreds) {
        String igs = "";
        for (int m = 0; m < ingreds.length; m++) {
            igs = igs+ingreds[m] + "\n";
        }
        return  igs;
    }
    public String getCategory() { return category;}
    public String getArea(){return area;}

    public String getInstructions() {
        return instructions;
    }

    public String getIngredients_ () {
        return ingredients_;
    }
    public String getMealThumb() {
        return mealThumb;
    }

    public String getTitle() {
        return title;
    }
}
