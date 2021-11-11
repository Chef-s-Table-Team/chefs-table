package com.example.chefstable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.chefstable.adapters.RecipeAdapter;
import com.example.chefstable.fragments.ComposeFragment;
import com.example.chefstable.fragments.PostsFragment;
import com.example.chefstable.models.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String SEARCH_URL="https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    List<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // this is where all the posts logic will go
        RecyclerView rvRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();

        //create the adapter
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipes);
        // Set the adapter on the recycler view
        rvRecipes.setAdapter(recipeAdapter);
        //Set Layout Manager on the recycler view
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(SEARCH_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    Log.i(TAG,"Meals:"+ meals.toString());
                    recipes.addAll(Recipe.fromJsonArray(meals));
                    recipeAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Recipes: " + recipes.size());
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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment(); // place holder
                        break;
                    case R.id.action_profile:
                    //    fragment = new ProfileFragment();
                        fragment = new ComposeFragment(); // place holder
                        break;
                    case R.id.action_search:
                    //    fragment = new SearchFragment();
                        fragment = new ComposeFragment(); // place holder
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    default:
                        fragment = new PostsFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }

        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}