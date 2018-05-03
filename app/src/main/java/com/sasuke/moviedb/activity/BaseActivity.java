package com.sasuke.moviedb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.androidadvance.topsnackbar.TSnackbar;
import com.sasuke.moviedb.MovieMania;
import com.sasuke.moviedb.R;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.NetworkPresenterImpl;
import com.sasuke.moviedb.presenter.NetworkPresenter;
import com.sasuke.moviedb.view.NetworkView;


public abstract class BaseActivity extends AppCompatActivity implements NetworkView {

    protected NetworkPresenter mNetworkPresenter;

    protected TSnackbar mSnackbar;

    public abstract void onSnackbarClick();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkPresenter = new NetworkPresenterImpl(this, MovieMania.get(BaseActivity.this).getNetworkManager());
        mNetworkPresenter.checkNetworkConnection();
    }

    protected void showSnackbar(ViewGroup viewGroup) {
        mSnackbar = TSnackbar.make(viewGroup, getString(R.string.no_internet_connection), TSnackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSnackbarClick();
            }
        });
        mSnackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white));
        mSnackbar.show();
    }

    protected void dismissSnackbar() {
        if (mSnackbar != null)
            mSnackbar.dismiss();
    }
}
