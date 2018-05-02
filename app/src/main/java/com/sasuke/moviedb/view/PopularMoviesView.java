package com.sasuke.moviedb.view;

import com.sasuke.moviedb.model.pojo.Result;

/**
 * Created by abc on 5/1/2018.
 */

public interface PopularMoviesView {
    void onGetPopularMoviesSuccess(Result result);

    void onGetPopularMoviesFailure(Throwable throwable);

    void onGetTopRatedSuccess(Result result);

    void onGetTopRatedFailure(Throwable throwable);
}
