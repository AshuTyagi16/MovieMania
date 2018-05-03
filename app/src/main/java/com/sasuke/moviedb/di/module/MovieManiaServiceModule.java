package com.sasuke.moviedb.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;
import com.sasuke.moviedb.network.MovieManiaService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 5/3/2018.
 */

@Module(includes = NetworkModule.class)
public class MovieManiaServiceModule {

    @Provides
    @MovieManiaApplicationScope
    public MovieManiaService getMovieManiaApi(Retrofit retrofit) {
        return retrofit.create(MovieManiaService.class);
    }

    @Provides
    @MovieManiaApplicationScope
    public Retrofit getRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @MovieManiaApplicationScope
    public Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
