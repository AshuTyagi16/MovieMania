package com.sasuke.moviedb.model;

import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.presenter.NetworkPresenter;
import com.sasuke.moviedb.view.NetworkView;

public class NetworkPresenterImpl implements NetworkPresenter {

    private NetworkView mNetworkView;

    public NetworkPresenterImpl(NetworkView networkView) {
        this.mNetworkView = networkView;
    }

    @Override
    public void checkNetworkConnection() {
        if (NetworkManager.getInstance().isConnected())
            mNetworkView.onNetworkAvailable();
        else
            mNetworkView.onNetworkFailed();
    }
}
