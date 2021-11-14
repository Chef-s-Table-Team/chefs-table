package com.example.chefstable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chefstable.R;
import com.example.chefstable.models.Post;
import com.parse.ParseFile;

import java.util.List;

// this will load the data and display it contingently
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
   private List<Post> posts;


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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser1 = itemView.findViewById(R.id.tvUser1);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivPost = itemView.findViewById(R.id.ivPost);
            ivProf1 = itemView.findViewById(R.id.ivProf1);
        }

        public void bind(Post post) {
            // Bind posts that we have to view the elements
            tvCaption.setText(post.getDescription());
            tvUser1.setText(post.getUser().getUsername());
            // make sure to add profile picture snippet
            ParseFile image = post.getImage(); // get the picture
            ParseFile profile = post.getProfile();
            if (image != null) {
                // we need to import Glide libraries
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }
            if (profile != null) {
                Glide.with(context).load(post.getProfile().getUrl()).into(ivProf1);
            }
        }
    }

}
