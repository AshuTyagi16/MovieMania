package com.sasuke.moviedb.model;

import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.network.MovieManiaApi;
import com.sasuke.moviedb.presenter.PopularMoviesPresenter;
import com.sasuke.moviedb.view.PopularMoviesView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 5/1/2018.
 */

public class PopularMoviesPresenterImpl implements PopularMoviesPresenter {

    private PopularMoviesView mPopularMoviesView;

    public PopularMoviesPresenterImpl(PopularMoviesView popularMoviesView) {
        this.mPopularMoviesView = popularMoviesView;
    }

    @Override
    public void getPopularMovies(String api_key, int page) {
        if (NetworkManager.getInstance().isConnected()) {
            MovieManiaApi.getInstance().getPopularMovies(api_key, page).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    mPopularMoviesView.onGetPopularMoviesSuccess(response.body());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    mPopularMoviesView.onGetPopularMoviesFailure(t);
                }
            });
        } else {
            mPopularMoviesView.onShowNetworkConnectionError();
        }
    }
}
