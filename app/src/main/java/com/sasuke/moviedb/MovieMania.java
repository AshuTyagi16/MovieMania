package com.sasuke.moviedb;

import android.app.Application;
import android.content.Context;

/**
 * Created by abc on 5/1/2018.
 */

public class MovieMania extends Application {

    private static MovieMania instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance;
    }
}
