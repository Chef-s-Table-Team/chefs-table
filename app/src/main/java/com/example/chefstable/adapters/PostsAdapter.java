package com.example.chefstable.adapters;

import android.content.Context;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.chefstable.DetailedRecipeActivity;
import com.example.chefstable.ProfileActivity;

import com.example.chefstable.ComposeActivity;

import com.example.chefstable.R;
import com.example.chefstable.models.Post;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;
import com.parse.ParseFile;

import java.util.List;

// this will load the data and display it contingently
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
   private List<Post> posts;
   //private int changes;


    public PostsAdapter (Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;

    }


    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
       return posts.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProf1, ivPost;
        private TextView tvUser1, tvCaption;
        private RoundedProgressBar chefProgress;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser1 = itemView.findViewById(R.id.tvUser1);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivPost = itemView.findViewById(R.id.ivPost);
            ivProf1 = itemView.findViewById(R.id.ivProf1); // profile picture
            chefProgress = itemView.findViewById(R.id.chefProgress); // progress bar

        }

        public void bind(Post post) {
            // Bind posts that we have to view the elements
            tvCaption.setText(post.getDescription());
            tvUser1.setText("@" + post.getUser().getUsername());
            // make sure to add profile picture snippet
            ParseFile image = post.getImage(); // get the picture
            ParseFile profile = post.getProfilePicture();
           // chefProgress.showProgressText(true);
            // get the progress of the user

            if(post.getProgress() == 0) {
                chefProgress.setVisibility(View.INVISIBLE);
            }

            if(post.getProgress() == 1) {
                chefProgress.setBackgroundDrawableColor(context.getResources().getColor(R.color.pbPrep));
                chefProgress.setProgressPercentage(25, true);
                chefProgress.showProgressText(true);
            }
            if (post.getProgress() == 2) {
                chefProgress.setBackgroundDrawableColor(context.getResources().getColor(R.color.pbCook));
                chefProgress.setProgressPercentage(50, true);
                chefProgress.showProgressText(true);
            }

            if (post.getProgress() == 3) {
                chefProgress.setBackgroundDrawableColor(context.getResources().getColor(R.color.pbReady));
                chefProgress.setProgressPercentage(100, true);
                chefProgress.showProgressText(true);
            }
            if (image != null) {
                // we need to import Glide libraries
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }
            if (profile != null) { // profile
                Glide.with(context).load(post.getProfilePicture().getUrl()).into(ivProf1);
            }


            tvUser1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ProfileActivity.class);
                    Log.i("test","" + post.getUser());
                    i.putExtra("post", (Parcelable) post);
                    context.startActivity(i);
                }
            });

        }

    }



}
