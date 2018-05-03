package com.sasuke.moviedb.di.component;

import com.sasuke.moviedb.di.module.MovieManiaServiceModule;
import com.sasuke.moviedb.di.module.PicassoModule;
import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;
import com.sasuke.moviedb.network.MovieManiaService;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by abc on 5/3/2018.
 */

@Component(modules = {MovieManiaServiceModule.class, PicassoModule.class})
@MovieManiaApplicationScope
public interface MovieManiaApplicationComponent {
    Picasso getPicasso();

    MovieManiaService getMovieManiaService();
}
