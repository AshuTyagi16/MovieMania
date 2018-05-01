package com.sasuke.moviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.pojo.Result;

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
        Gson gson = new GsonBuilder().create();
        OkHttpClient httpClient = createHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(MovieManiaApiInterface.class);
    }

    /********Api Calls********/

    public Call<Result> getPopularMovies(String api_key) {
        return service.getPopularMovies(api_key);
    }

    public Call<Result> getTopRatedMovies(String api_key) {
        return service.getTopRatedMovies(api_key);
    }

    /********Interceptor********/

    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging);

        return builder.build();
    }
}
