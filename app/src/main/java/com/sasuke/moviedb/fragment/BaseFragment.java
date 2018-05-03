package com.sasuke.moviedb.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sasuke.moviedb.model.NetworkPresenterImpl;
import com.sasuke.moviedb.presenter.NetworkPresenter;
import com.sasuke.moviedb.view.NetworkView;

import butterknife.ButterKnife;

/**
 * Created by abc on 5/3/2018.
 */

public abstract class BaseFragment extends Fragment implements NetworkView {

    @LayoutRes
    abstract protected int getLayoutResId();

    protected NetworkPresenter mNetworkPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        mNetworkPresenter = new NetworkPresenterImpl(this);
        mNetworkPresenter.checkNetworkConnection();
        return view;
    }
}
