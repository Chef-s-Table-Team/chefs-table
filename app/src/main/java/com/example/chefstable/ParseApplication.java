package com.example.chefstable;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {

    //initialize parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("XXkbWs31c2ZDZ6k6AHfeu0TTaItYIvrh7Mq2VNaj")
                .clientKey("30b4AMT92yMwvDe8vZcewswvid7ODSJdZkKkD7N3")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
