package com.example.chefstable.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chefstable.DetailedRecipeActivity;
import com.example.chefstable.R;
import com.example.chefstable.models.Recipe;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    Context context;
    List<Recipe> recipes;


    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    public void clear(){
        recipes.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Recipe> recipe_list) {
        recipes.addAll(recipe_list);
        notifyDataSetChanged();

    }

    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("RecipeAdapter","onCreateViewHolder");
        View recipeView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(recipeView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("RecipeAdapter","onBindViewHolder" + position);
        //Get the recipe at the passes in the position
        Recipe recipe = recipes.get(position);

        //Bind the movie data into the ViewHolder
        holder.bind(recipe);
    }

    //Return the total count of items in the list
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle, tvCategory, tvArea;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvArea = itemView.findViewById(R.id.tvArea);
        }

        public void bind(Recipe recipe) {
            tvTitle.setText(recipe.getTitle());
            tvCategory.setText(recipe.getCategory());
            tvArea.setText(recipe.getArea());

            String imgURL;

            //get thumbnail for recipe

            imgURL = recipe.getMealThumb();

           Glide.with(context).load(recipe.getMealThumb()).into(ivPoster);

            //register click listener to whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //go to detail recipe

                    Intent i = new Intent(context, DetailedRecipeActivity.class); //import qian + maria's file

                    i.putExtra("strMeal", Parcels.wrap(recipe)); // pass the strMeal to a recipe object so it can hold it
                    context.startActivity(i);

                }
            });
        }
    }

}
