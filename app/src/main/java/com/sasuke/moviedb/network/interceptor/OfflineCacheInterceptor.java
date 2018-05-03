package com.sasuke.moviedb.network.interceptor;


import com.sasuke.moviedb.manager.NetworkManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class OfflineCacheInterceptor implements Interceptor {


    private NetworkManager networkManager;

    public OfflineCacheInterceptor(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!networkManager.isConnected()) {
            request = request.newBuilder().cacheControl(new CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build()).build();
        }
        return chain.proceed(request);
    }
}