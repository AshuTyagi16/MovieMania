package com.sasuke.moviedb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.adapter.MoviesAdapter;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.PopularMoviesPresenterImpl;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.presenter.PopularMoviesPresenter;
import com.sasuke.moviedb.util.ItemDecorator;
import com.sasuke.moviedb.view.PopularMoviesView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PopularMoviesView {

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;

    private PopularMoviesPresenter mMoviesPresenter;

    private MoviesAdapter mMoviesAdapter;
    private static final int SPAN_COUNT = 2;
    private static final int SPACEING = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMoviesPresenter = new PopularMoviesPresenterImpl(this);
        mRvMovies.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(SPACEING));
        mMoviesAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mMoviesAdapter);
        mMoviesPresenter.getPopularMovies(Constants.API_KEY);
    }

    @Override
    public void onGetPopularMoviesSuccess(Result result) {
        mMoviesAdapter.setMovies(result);
    }

    @Override
    public void onGetPopularMoviesFailure(Throwable throwable) {
        showFailurePlaceholder();
    }

    @Override
    public void onShowNetworkConnectionError() {
        showNetworkFailurePlaceholder();
    }

    private void showFailurePlaceholder() {

    }

    private void showNetworkFailurePlaceholder() {

    }
}
