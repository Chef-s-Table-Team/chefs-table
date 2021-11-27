package com.example.chefstable.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chefstable.R;

import com.example.chefstable.models.User;


import com.example.chefstable.adapters.PostsAdapter;
import com.example.chefstable.models.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private RecyclerView rvProfilePosts;

    ImageView profPic;
    TextView tvUser, tvBio;
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
        ParseUser user = ParseUser.getCurrentUser(); // stores the current user that is logged in
        profPic = view.findViewById(R.id.imageView);
        tvUser = view.findViewById(R.id.tvUser);
        tvBio = view.findViewById(R.id.tvBio);
        rvProfilePosts = view.findViewById(R.id.rvProfilePosts);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerProfile);

        allProfilePosts = new ArrayList<>();
        profileAdapter = new PostsAdapter(getContext(), allProfilePosts);
        rvProfilePosts.setAdapter(profileAdapter);
        rvProfilePosts.setLayoutManager(new LinearLayoutManager(getContext()));

        Glide.with(view).load(ParseUser.getCurrentUser().getParseFile("profile")).into(profPic);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });

    }

    protected void queryPosts() {
        //set class for query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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