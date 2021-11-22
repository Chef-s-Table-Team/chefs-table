package com.example.chefstable.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    String mealThumb;
    String title, instructions;
    String ingredients [];
    String measurements[];

    public Recipe () {}

    public Recipe(JSONObject jsonObject) throws JSONException {
        mealThumb=jsonObject.getString("strMealThumb");
        title=jsonObject.getString("strMeal");
        instructions = jsonObject.getString("strInstructions");

    }

    public static List<Recipe> fromJsonArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes=new ArrayList<>();
        for(int i=0; i < recipeJsonArray.length(); i++) {
            recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public String getTitle() {
        return title;
    }
}
