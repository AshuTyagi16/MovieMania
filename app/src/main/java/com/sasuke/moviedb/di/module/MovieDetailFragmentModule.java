package com.sasuke.moviedb.di.module;

import com.sasuke.moviedb.activity.MovieDetailActivity;
import com.sasuke.moviedb.db.MovieManiaDatabaseAdapter;
import com.sasuke.moviedb.di.scope.PerActivityScope;
import com.sasuke.moviedb.model.MovieDetailPresenterImpl;
import com.sasuke.moviedb.network.MovieManiaService;
import com.sasuke.moviedb.presenter.MovieDetailPresenter;
import com.sasuke.moviedb.view.MovieDetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/3/2018.
 */

@Module
public class MovieDetailFragmentModule {

    private final MovieDetailView movieDetailView;
    private final MovieDetailActivity movieDetailActivity;

    public MovieDetailFragmentModule(MovieDetailActivity movieDetailActivity, MovieDetailView movieDetailView) {
        this.movieDetailActivity = movieDetailActivity;
        this.movieDetailView = movieDetailView;
    }

    @Provides
    @PerActivityScope
    public MovieDetailPresenter getMovieDetailPresenter(MovieManiaService movieManiaService) {
        return new MovieDetailPresenterImpl(movieManiaService, movieDetailView);
    }

    @Provides
    @PerActivityScope
    public MovieManiaDatabaseAdapter getDatabaseAdapter() {
        return MovieManiaDatabaseAdapter.getInstance(movieDetailActivity);
    }
}
