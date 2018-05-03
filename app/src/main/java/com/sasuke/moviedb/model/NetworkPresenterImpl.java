package com.sasuke.moviedb.model;

import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.presenter.NetworkPresenter;
import com.sasuke.moviedb.view.NetworkView;

public class NetworkPresenterImpl implements NetworkPresenter {

    private NetworkView mNetworkView;
    private NetworkManager networkManager;

    public NetworkPresenterImpl(NetworkView networkView, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.mNetworkView = networkView;
    }

    @Override
    public void checkNetworkConnection() {
        if (networkManager.isConnected())
            mNetworkView.onNetworkAvailable();
        else
            mNetworkView.onNetworkFailed();
    }
}
