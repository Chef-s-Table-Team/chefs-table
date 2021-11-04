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
                .applicationId("1pGDEKltNUcp5FPAWSWvWbWFULcNasHHH7zBcK3T")
                .clientKey("cHPmA3lxcHpI6HKv2Ih2IylINmV3cze9BWjaJ0q6")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
