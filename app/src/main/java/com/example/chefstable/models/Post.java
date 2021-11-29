package com.example.chefstable.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName(("Post"))
public class Post extends ParseObject implements Serializable {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_PROGRESS = "progress";
    
    public ParseFile getProfilePicture () { return getUser().getParseFile(KEY_PROFILE); }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public int getProgress() { return getInt(KEY_PROGRESS);}


    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }
    public void setKeyProgress(int progress) {put(KEY_PROGRESS, progress);}

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public void setProfile(ParseUser user) {put(KEY_PROFILE, user);}
}

