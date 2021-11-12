package com.example.chefstable.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    String mealThumb;
    String title;


    public Recipe(JSONObject jsonObject) throws JSONException {
        mealThumb=jsonObject.getString("strMealThumb");
        title=jsonObject.getString("strMeal");
    }

    public static List<Recipe> fromJsonArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes=new ArrayList<>();
        for(int i=0; i < recipeJsonArray.length(); i++) {
            recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public String getTitle() {
        return title;
    }
}
