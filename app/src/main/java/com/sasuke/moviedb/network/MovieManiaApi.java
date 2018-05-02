package com.sasuke.moviedb.network;

import com.sasuke.moviedb.MovieMania;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.pojo.MovieDetail;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.network.interceptor.CacheInterceptor;
import com.sasuke.moviedb.network.interceptor.OfflineCacheInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 5/1/2018.
 */

public class MovieManiaApi {

    private static final String BASE_API_URL = Constants.BASE_URL;

    private static volatile MovieManiaApi instance;

    private MovieManiaApiInterface service;

    private MovieManiaApi() {
    }

    public static MovieManiaApi getInstance() {
        if (instance == null) {
            synchronized (MovieManiaApi.class) {
                if (instance == null) {
                    instance = new MovieManiaApi();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        service = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.addInterceptor(provideOfflineCacheInterceptor())
                        .addInterceptor(createLoggingInterceptor())
                        .addNetworkInterceptor(provideCacheInterceptor())
                        .cache(provideCache())
                        .build())
                .build()
                .create(MovieManiaApiInterface.class);
    }


    /********Api Calls********/

    public Call<Result> getPopularMovies(String api_key, int page) {
        return service.getPopularMovies(api_key, page);
    }

    public Call<Result> getTopRatedMovies(String api_key, int page) {
        return service.getTopRatedMovies(api_key, page);
    }

    public Call<MovieDetail> getMovieDetail(String api_key, int movie_id) {
        return service.getMovieDetail(movie_id, api_key);
    }

    /****** Caching******/

    private Cache provideCache() {
        try {
            return new Cache(new File(MovieMania.getAppContext().getCacheDir(), Constants.CACHE_DIR), Constants.CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Interceptor provideCacheInterceptor() {
        return new CacheInterceptor();
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return new OfflineCacheInterceptor();
    }


    /********Logging Interceptor********/

    private HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
