package com.sasuke.moviedb.network;

import com.sasuke.moviedb.model.pojo.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abc on 5/1/2018.
 */

public interface MovieManiaApiInterface {

    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<Result> getTopRatedMovies(@Query("api_key") String api_key);
}
