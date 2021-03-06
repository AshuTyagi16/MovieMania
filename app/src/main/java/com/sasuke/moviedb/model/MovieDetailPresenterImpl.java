package com.sasuke.moviedb.model;

import com.sasuke.moviedb.model.pojo.MovieDetail;
import com.sasuke.moviedb.network.MovieManiaService;
import com.sasuke.moviedb.presenter.MovieDetailPresenter;
import com.sasuke.moviedb.view.MovieDetailView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 5/3/2018.
 */

public class MovieDetailPresenterImpl implements MovieDetailPresenter {

    private MovieDetailView mMovieDetailView;
    private MovieManiaService movieManiaService;

    public MovieDetailPresenterImpl(MovieManiaService movieManiaService, MovieDetailView movieDetailView) {
        this.movieManiaService = movieManiaService;
        this.mMovieDetailView = movieDetailView;
    }

    @Override
    public void getMovieDetail(String apiKey, int movieId) {
        movieManiaService.getMovieDetail(movieId, apiKey).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                mMovieDetailView.onGetMovieDetailSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                mMovieDetailView.onGetMovieDetailFailure(t);
            }
        });
    }
}
