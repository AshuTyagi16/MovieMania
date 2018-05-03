package com.sasuke.moviedb.di.module;

import android.content.Context;

import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;
import com.sasuke.moviedb.manager.PreferenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/3/2018.
 */

@Module
public class PreferenceManagerModule {

    @Provides
    @MovieManiaApplicationScope
    public PreferenceManager getPreferenceManager(Context context) {
        return new PreferenceManager(context);
    }
}
