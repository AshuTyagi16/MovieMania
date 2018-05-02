package com.sasuke.moviedb.network.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;

public class CacheInterceptor implements Interceptor {

    private static final String CACHE_CONTROL = "Cache-Control";

    public okhttp3.Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request())
                .newBuilder()
                .header(CACHE_CONTROL,
                        new CacheControl.Builder()
                                .maxAge(0, TimeUnit.MINUTES)
                                .build()
                                .toString())
                .build();
    }
}
