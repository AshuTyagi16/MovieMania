package com.sasuke.moviedb.di.module;

import android.content.Context;

import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;
import com.sasuke.moviedb.manager.NetworkManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/3/2018.
 */

@Module
public class NetworkManagerModule {

    @Provides
    @MovieManiaApplicationScope
    public NetworkManager getNetworkManager(Context context) {
        return new NetworkManager(context);
    }
}
