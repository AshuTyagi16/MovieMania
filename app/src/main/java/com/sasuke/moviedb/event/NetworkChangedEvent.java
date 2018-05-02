package com.sasuke.moviedb.event;

public class NetworkChangedEvent {

    public boolean isNetworkAvailable;

    public NetworkChangedEvent(boolean isNetworkAvailable) {
        this.isNetworkAvailable = isNetworkAvailable;
    }
}
