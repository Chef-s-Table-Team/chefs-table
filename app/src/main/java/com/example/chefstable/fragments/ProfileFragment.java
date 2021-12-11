package com.example.chefstable.fragments;

import static com.parse.ParseUser.getCurrentUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.example.chefstable.EditActivity;
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


    Post post;
    private RecyclerView rvProfilePosts;

    ImageView profPic;
    TextView tvUser, tvBio,tvRcp, tvDetail;
    private Button btnLogout, btnEd;
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
        profPic = view.findViewById(R.id.imageView);
        tvUser = view.findViewById(R.id.tvUserAct);
        tvBio = view.findViewById(R.id.tvBio);
        tvDetail = view.findViewById(R.id.tvDetail);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEd = view.findViewById(R.id.btnEd);
        rvProfilePosts = view.findViewById(R.id.rvProfilePosts);
        tvRcp = view.findViewById(R.id.tvRcp);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerProfile);
        ParseUser currentUser = ParseUser.getCurrentUser();
        post = new Post();
        post.setUser(currentUser); // for current logged in user
        ParseFile profile = post.getProfilePicture();

        tvBio.setText(ParseUser.getCurrentUser().getString("bio"));
        if (profile != null) { // profile
            Glide.with(getContext()).load(post.getProfilePicture().getUrl()).into(profPic);
        }
        tvUser.setText("@" + post.getUser().getUsername());
       // Log.i("BIO", post.getBio());

        allProfilePosts = new ArrayList<>();
        profileAdapter = new PostsAdapter(getContext(), allProfilePosts);
        rvProfilePosts.setAdapter(profileAdapter);
        rvProfilePosts.setLayoutManager(new GridLayoutManager(getContext(), 2));


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

        btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditActivity();
            }
        });
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

    private void goEditActivity() {
        Intent m = new Intent(getContext(), EditActivity.class);
        startActivity(m);
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

