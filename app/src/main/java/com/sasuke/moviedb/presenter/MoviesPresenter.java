package com.sasuke.moviedb.presenter;

/**
 * Created by abc on 5/1/2018.
 */

public interface MoviesPresenter {
    void getPopularMovies(String api_key, int page);

    void getTopRatedMovies(String api_key, int page);
}
