package com.sasuke.moviedb.di.component;

import com.sasuke.moviedb.db.MovieManiaDatabaseAdapter;
import com.sasuke.moviedb.di.module.MovieDetailFragmentModule;
import com.sasuke.moviedb.di.scope.PerActivityScope;
import com.sasuke.moviedb.presenter.MovieDetailPresenter;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by abc on 5/3/2018.
 */

@PerActivityScope
@Component(modules = MovieDetailFragmentModule.class, dependencies = MovieManiaApplicationComponent.class)
public interface MovieDetailFragmentComponent {

    MovieDetailPresenter getMovieDetailPresenter();

    MovieManiaDatabaseAdapter getDatabaseAdapter();

    Picasso getPicasso();
}
