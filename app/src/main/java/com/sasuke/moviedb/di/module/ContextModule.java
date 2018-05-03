package com.sasuke.moviedb.di.module;

import android.content.Context;

import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/3/2018.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @MovieManiaApplicationScope
    public Context getContext() {
        return context;
    }
}
