package com.example.chefstable.fragments;

import static com.parse.ParseUser.getCurrentUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chefstable.LoginActivity;
import com.example.chefstable.R;


import com.example.chefstable.adapters.PostsAdapter;

import com.example.chefstable.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;


import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;



public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    ParseUser user;
    Post post;
    private RecyclerView rvProfilePosts;

    ImageView profPic;
    TextView tvUser, tvBio,tvRcp;
    private Button btnLogout;
    protected PostsAdapter profileAdapter;

    protected List<Post> allProfilePosts;

    SwipeRefreshLayout swipeContainer;


    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = getCurrentUser(); // stores the current user that is logged in
        profPic = view.findViewById(R.id.imageView);
        tvUser = view.findViewById(R.id.tvUserAct);
        tvBio = view.findViewById(R.id.tvBio);
        btnLogout = view.findViewById(R.id.btnLogout);
        rvProfilePosts = view.findViewById(R.id.rvProfilePosts);
        tvRcp = view.findViewById(R.id.tvRcp);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerProfile);

       // tvUser.setText("@"+post.getCurrentUser().getUsername());

        /*ParseFile profile = post.getProfilePicture(); // this loads a stove LOL


        if (profile != null) {
            // we need to import Glide libraries
            Glide.with(this).load(post.getProfilePicture().getUrl()).into(profPic);
        }
        */
        allProfilePosts = new ArrayList<>();
        profileAdapter = new PostsAdapter(getContext(), allProfilePosts);
        rvProfilePosts.setAdapter(profileAdapter);
        rvProfilePosts.setLayoutManager(new GridLayoutManager(getContext(), 1));


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                queryPosts();
                profileAdapter.clear();
                profileAdapter.addAll(allProfilePosts);
                swipeContainer.setRefreshing(false);
            }
        });
        queryPosts();
        btnLogout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curUser = ParseUser.getCurrentUser().getUsername();
                ParseUser.logOut();
                //Toast.makeText(MainActivity.this, "User logged out!", Toast.LENGTH_SHORT).show();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                if(currentUser == null){
                    Toast.makeText(getContext(), curUser + " logged out!", Toast.LENGTH_SHORT).show();
                }
                goLoginActivity();
            }
        }));
    }
    private void goLoginActivity(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
    protected void queryPosts() {
        //set class for query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, getCurrentUser());
        query.setLimit(20);

        query.addDescendingOrder(Post.KEY_CREATED_KEY); //posts go from most -> least recent post
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                //update adapter w/current posts
                allProfilePosts.addAll(posts);
                profileAdapter.notifyDataSetChanged();
                // swipeContainer.setRefreshing(false);
            }
        });
    } //retrieve all posts

}

