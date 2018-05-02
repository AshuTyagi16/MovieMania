package com.sasuke.moviedb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sasuke.moviedb.model.NetworkPresenterImpl;
import com.sasuke.moviedb.presenter.NetworkPresenter;
import com.sasuke.moviedb.view.NetworkView;


public abstract class BaseActivity extends AppCompatActivity implements NetworkView {

    protected NetworkPresenter mNetworkPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkPresenter = new NetworkPresenterImpl(this);
        mNetworkPresenter.checkNetworkConnection();
    }
}
