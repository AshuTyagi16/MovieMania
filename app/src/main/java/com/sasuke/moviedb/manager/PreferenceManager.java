package com.sasuke.moviedb.manager;

/**
 * Created by abc on 4/23/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sasuke on 1/24/2018.
 */

public class PreferenceManager {

    private static final String MY_PREFS = "perferences";
    private static final boolean DEFAULT_STATUS = false;
    private static final String EXTRA_CACHE_STATUS = "cache_status";

    private Context appContext;

    public PreferenceManager(Context appContext) {
        this.appContext = appContext;
    }

    public void updateCacheStatus(boolean status) {
        SharedPreferences.Editor editor = appContext.getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        editor.putBoolean(EXTRA_CACHE_STATUS, status);
        editor.apply();
    }

    public boolean isDataInCache() {
        SharedPreferences prefs = appContext.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        return prefs.getBoolean(EXTRA_CACHE_STATUS, DEFAULT_STATUS);
    }

}

