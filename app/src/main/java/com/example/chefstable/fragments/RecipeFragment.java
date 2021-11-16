package com.example.chefstable.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.chefstable.R;
import com.example.chefstable.adapters.RecipeAdapter;
import com.example.chefstable.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class RecipeFragment extends Fragment {
    public static final String TAG = "RecipeFragment";
    public static final String SEARCH_URL="https://www.themealdb.com/api/json/v1/1/search.php?s=";
    private RecyclerView rvRecipes;
    RecipeAdapter rAdapter;
    List<Recipe> allRecipes;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRecipes = view.findViewById(R.id.rvRecipes);
        allRecipes = new ArrayList<>();
        rAdapter =  new RecipeAdapter(getContext(),allRecipes);
        rvRecipes.setAdapter(rAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        rAdapter.addAll(allRecipes); // display ALL the recipes

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(SEARCH_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    Log.i(TAG,"Meals:"+ meals.toString());
                    allRecipes.addAll(Recipe.fromJsonArray(meals));
                    rAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Recipes: " + allRecipes.size());
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception",e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }
}