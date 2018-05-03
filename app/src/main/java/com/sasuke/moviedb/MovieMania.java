package com.sasuke.moviedb;

import android.app.Activity;
import android.app.Application;

import com.sasuke.moviedb.di.component.DaggerMovieManiaApplicationComponent;
import com.sasuke.moviedb.di.component.MovieManiaApplicationComponent;
import com.sasuke.moviedb.di.module.ContextModule;
import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.manager.PreferenceManager;

import timber.log.Timber;

/**
 * Created by abc on 5/1/2018.
 */

public class MovieMania extends Application {

    private NetworkManager networkManager;
    private PreferenceManager preferenceManager;
    private MovieManiaApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        component = DaggerMovieManiaApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        networkManager = component.getNetworkManager();
        preferenceManager = component.getPreferenceManager();
    }

    public static MovieMania get(Activity activity) {
        return (MovieMania) activity.getApplication();
    }

    public MovieManiaApplicationComponent getApplicationComponent() {
        return component;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }
}
