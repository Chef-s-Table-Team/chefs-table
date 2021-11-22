package com.example.chefstable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chefstable.models.Post;
import com.example.chefstable.models.Recipe;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private int numPosts;
    public static final String TAG = "ProfileActivity";
    ParseUser user;
    ParseUser ivProfile;
    TextView tvNumPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNumPost = findViewById(R.id.tvNumPosts);

        Post post = (Post) getIntent().getSerializableExtra("post");
        user = post.getUser();
        ivProfile = post.getUser();
        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.whereEqualTo("user", user);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "empty", e);
                }
                numPosts = posts.size();
                tvNumPost.setText("" + numPosts);
                Log.i(TAG, "Num of Posts: " + numPosts);
            }
        });

    }
}