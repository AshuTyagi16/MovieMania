package com.sasuke.moviedb.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sasuke.moviedb.event.NetworkChangedEvent;
import com.sasuke.moviedb.manager.NetworkManager;

import org.greenrobot.eventbus.EventBus;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (NetworkManager.getInstance().isConnected())
            EventBus.getDefault().postSticky(new NetworkChangedEvent(true));
        else
            EventBus.getDefault().postSticky(new NetworkChangedEvent(false));

    }
}
