package com.sasuke.moviedb.view;

import com.sasuke.moviedb.model.pojo.MovieDetail;

/**
 * Created by abc on 5/3/2018.
 */

public interface MovieDetailView {
    void onGetMovieDetailSuccess(MovieDetail movieDetail);

    void onGetMovieDetailFailure(Throwable throwable);
}
