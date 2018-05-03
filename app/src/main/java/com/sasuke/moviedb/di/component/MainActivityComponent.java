package com.sasuke.moviedb.di.component;

import android.support.v7.widget.GridLayoutManager;

import com.sasuke.moviedb.adapter.MoviesAdapter;
import com.sasuke.moviedb.di.module.MainActivityModule;
import com.sasuke.moviedb.di.scope.PerActivityScope;
import com.sasuke.moviedb.presenter.MoviesPresenter;
import com.sasuke.moviedb.util.ItemDecorator;
import com.sasuke.moviedb.util.NetworkChangeReceiver;

import dagger.Component;

/**
 * Created by abc on 5/3/2018.
 */

@PerActivityScope
@Component(modules = MainActivityModule.class, dependencies = {MovieManiaApplicationComponent.class})
public interface MainActivityComponent {

    MoviesPresenter getMoviesPresenter();

    MoviesAdapter getMoviesAdapter();

    ItemDecorator getItemDecorator();

    GridLayoutManager getGridLayoutManager();

    NetworkChangeReceiver getNetworkChangeReceiver();
}
