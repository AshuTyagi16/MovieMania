package com.sasuke.moviedb.manager;

/**
 * Created by abc on 5/1/2018.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sasuke.moviedb.MovieMania;


public class NetworkManager {

    private static volatile NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    private NetworkManager() {

    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) MovieMania.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected()
                && !networkInfo.isRoaming();
    }

}
