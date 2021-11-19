package com.example.chefstable.models;

import static com.example.chefstable.models.Post.KEY_USER;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName(("User"))
public class User extends ParseObject {
    public static final String KEY_BIO = "bio";
    public static final String KEY_NUMPOSTS = "numPosts";
    public static final String KEY_USER = "user";
    public static final String KEY_PROFILE = "profile";

    // getters
    public ParseFile getProfilePicture () { return getParseFile(KEY_PROFILE);}
    public String getBio () { return getString(KEY_BIO); }
    public int getPosts () { return getInt(KEY_NUMPOSTS); } // num posts
    public ParseUser getUser () { return getParseUser(KEY_USER);}

    // setters
    public void setBio (String s) { put(KEY_BIO, s); }
    public void setPostCount(int p) {put(KEY_NUMPOSTS, p);}
    public void setProfile(ParseFile prof) {put(KEY_PROFILE, prof);}




}
