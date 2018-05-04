package com.sasuke.moviedb.di.module;

import android.content.Context;

import com.sasuke.moviedb.db.MovieManiaDatabaseAdapter;
import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/4/2018.
 */

@Module
public class DatabaseModule {

    private Context mAppContext;

    public DatabaseModule(Context appContext) {
        this.mAppContext = appContext;
    }

    @Provides
    @MovieManiaApplicationScope
    public MovieManiaDatabaseAdapter getDatabaseAdapter() {
        return MovieManiaDatabaseAdapter.getInstance(this.mAppContext);
    }
}
