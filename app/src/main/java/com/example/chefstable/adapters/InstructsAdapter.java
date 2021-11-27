package com.example.chefstable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chefstable.R;
import com.example.chefstable.models.Recipe;

import java.util.List;

public class InstructsAdapter extends RecyclerView.Adapter<InstructsAdapter.ViewHolder> {
    Context conts;
    List<Recipe> instRecs;

    public InstructsAdapter (Context conts, List<Recipe> inst) {
        this.conts = conts;
        this.instRecs = inst;
    }

    public void clear() {
        instRecs.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Recipe> recs_list) {
        instRecs.addAll(recs_list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recsView = LayoutInflater.from(conts).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(recsView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructsAdapter.ViewHolder holder, int position) {
        Recipe recs = instRecs.get(position);
        holder.bind(recs);
    }


    @Override
    public int getItemCount() {
        return instRecs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        CheckBox cbIngredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.LinContainer);
            cbIngredient = itemView.findViewById(R.id.cbIngreds);
        }

        public void bind(Recipe recipe) {
            cbIngredient.setText(recipe.getInstructions());
        }
    }
}
