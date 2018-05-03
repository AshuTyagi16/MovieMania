package com.sasuke.moviedb.manager;

/**
 * Created by abc on 5/1/2018.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class NetworkManager {

    private Context context;

    public NetworkManager(Context context) {
        this.context = context;
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected()
                && !networkInfo.isRoaming();
    }

}
