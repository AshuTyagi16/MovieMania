package com.sasuke.moviedb.di.module;

import android.content.Context;

import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.di.qualifier.NetworkCacheQualifier;
import com.sasuke.moviedb.di.qualifier.OfflineCacheQualifier;
import com.sasuke.moviedb.di.scope.MovieManiaApplicationScope;
import com.sasuke.moviedb.network.interceptor.CacheInterceptor;
import com.sasuke.moviedb.network.interceptor.OfflineCacheInterceptor;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by abc on 5/3/2018.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @MovieManiaApplicationScope
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache,
                                        @OfflineCacheQualifier Interceptor offlineCacheInterceptor,
                                        @NetworkCacheQualifier Interceptor networkCacheInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(offlineCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(networkCacheInterceptor)
                .cache(cache);

        return builder.build();
    }

    @Provides
    @MovieManiaApplicationScope
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @MovieManiaApplicationScope
    public Cache getCache(File cacheFile) {
        return new Cache(cacheFile, Constants.CACHE_SIZE);
    }

    @Provides
    @MovieManiaApplicationScope
    public File getCacheFile(Context context) {
        return new File(context.getCacheDir(), Constants.CACHE_DIR);
    }

    @Provides
    @MovieManiaApplicationScope
    @OfflineCacheQualifier
    public Interceptor getOfflineCacheInterceptor() {
        return new OfflineCacheInterceptor();
    }

    @Provides
    @MovieManiaApplicationScope
    @NetworkCacheQualifier
    public Interceptor getCacheInterceptor() {
        return new CacheInterceptor();
    }
}
