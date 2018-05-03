package com.sasuke.moviedb.di.component;

import com.sasuke.moviedb.di.module.MovieManiaServiceModule;
import com.sasuke.moviedb.di.module.NetworkManagerModule;
import com.sasuke.moviedb.di.module.PicassoModule;
import com.sasuke.moviedb.di.module.PreferenceManagerModule;
import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;
import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.manager.PreferenceManager;
import com.sasuke.moviedb.network.MovieManiaService;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by abc on 5/3/2018.
 */

@MovieManiaApplicationScope
@Component(modules = {MovieManiaServiceModule.class, PicassoModule.class, NetworkManagerModule.class, PreferenceManagerModule.class})
public interface MovieManiaApplicationComponent {
    Picasso getPicasso();

    MovieManiaService getMovieManiaService();

    NetworkManager getNetworkManager();

    PreferenceManager getPreferenceManager();
}
