package com.sasuke.moviedb.model;

import com.sasuke.moviedb.MovieMania;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.presenter.MoviesPresenter;
import com.sasuke.moviedb.view.MoviesView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 5/1/2018.
 */

public class MoviesPresenterImpl implements MoviesPresenter {

    private MoviesView mMoviesView;

    public MoviesPresenterImpl(MoviesView moviesView) {
        this.mMoviesView = moviesView;
    }

    @Override
    public void getPopularMovies(String api_key, int page) {
        MovieMania.getAppContext().getMovieManiaService().getPopularMovies(api_key, page).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                mMoviesView.onGetPopularMoviesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                mMoviesView.onGetPopularMoviesFailure(t);
            }
        });
    }

    @Override
    public void getTopRatedMovies(String api_key, int page) {
        MovieMania.getAppContext().getMovieManiaService().getTopRatedMovies(api_key, page).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                mMoviesView.onGetTopRatedSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                mMoviesView.onGetTopRatedFailure(t);
            }
        });
    }
}
