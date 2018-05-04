package com.sasuke.moviedb.di.module;

import android.support.v7.widget.GridLayoutManager;

import com.sasuke.moviedb.activity.MainActivity;
import com.sasuke.moviedb.adapter.MoviesAdapter;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.db.MovieManiaDatabaseAdapter;
import com.sasuke.moviedb.di.scope.PerActivityScope;
import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.model.MoviesPresenterImpl;
import com.sasuke.moviedb.network.MovieManiaService;
import com.sasuke.moviedb.presenter.MoviesPresenter;
import com.sasuke.moviedb.util.ItemDecorator;
import com.sasuke.moviedb.util.LoadingListItemCreator;
import com.sasuke.moviedb.util.NetworkChangeReceiver;
import com.sasuke.moviedb.view.MoviesView;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/3/2018.
 */

@Module
public class MainActivityModule {

    private final MoviesView moviesView;
    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity activity, MoviesView moviesView) {
        this.mainActivity = activity;
        this.moviesView = moviesView;
    }

    @Provides
    @PerActivityScope
    public MoviesPresenter getMoviesPresenter(MovieManiaService movieManiaService) {
        return new MoviesPresenterImpl(movieManiaService, moviesView);
    }

    @Provides
    @PerActivityScope
    public MoviesAdapter getMoviesAdapter(Picasso picasso, MovieManiaDatabaseAdapter databaseAdapter) {
        return new MoviesAdapter(picasso, databaseAdapter);
    }

    @Provides
    @PerActivityScope
    public ItemDecorator getItemDecorator() {
        return new ItemDecorator(Constants.SPACING);
    }

    @Provides
    @PerActivityScope
    public GridLayoutManager getGridLayoutManager() {
        return new GridLayoutManager(mainActivity, Constants.SPAN_COUNT);
    }

    @Provides
    @PerActivityScope
    public NetworkChangeReceiver getNetworkChangeReceiver(NetworkManager networkManager) {
        return new NetworkChangeReceiver(networkManager);
    }

    @Provides
    @PerActivityScope
    public LoadingListItemCreator getLoadingListItemCreator() {
        return new LoadingListItemCreator();
    }
}
