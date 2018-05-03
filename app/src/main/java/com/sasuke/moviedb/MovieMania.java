package com.sasuke.moviedb;

import android.app.Application;
import android.content.Context;

import com.sasuke.moviedb.di.component.DaggerMovieManiaApplicationComponent;
import com.sasuke.moviedb.di.component.MovieManiaApplicationComponent;
import com.sasuke.moviedb.di.module.ContextModule;
import com.sasuke.moviedb.di.module.PicassoModule;
import com.sasuke.moviedb.network.MovieManiaService;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * Created by abc on 5/1/2018.
 */

public class MovieMania extends Application {

    private static MovieMania instance;

    private MovieManiaService movieManiaService;
    private Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());

        MovieManiaApplicationComponent component = DaggerMovieManiaApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .picassoModule(new PicassoModule())
                .build();

        movieManiaService = component.getMovieManiaService();
        picasso = component.getPicasso();
    }

    public static MovieMania getAppContext() {
        return instance;
    }

    public Picasso getPicasso(){
        return picasso;
    }
    public MovieManiaService getMovieManiaService(){
        return movieManiaService;
    }
}
