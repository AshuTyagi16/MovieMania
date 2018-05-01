package com.sasuke.moviedb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.paginate.Paginate;
import com.sasuke.moviedb.R;
import com.sasuke.moviedb.adapter.MoviesAdapter;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.PopularMoviesPresenterImpl;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.presenter.PopularMoviesPresenter;
import com.sasuke.moviedb.util.ItemDecorator;
import com.sasuke.moviedb.util.LoadingListItemCreator;
import com.sasuke.moviedb.view.PopularMoviesView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity implements PopularMoviesView, Paginate.Callbacks {

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;
    @BindView(R.id.pb_movies)
    CircularProgressBar mPbMovies;
    @BindView(R.id.iv_placeholder)
    ImageView mIvPlaceholder;

    private PopularMoviesPresenter mMoviesPresenter;

    private static final int INITIAL_PAGE = 1;
    private static final int SPAN_COUNT = 2;
    private static final int SPACING = 0;

    private MoviesAdapter mMoviesAdapter;

    private Paginate paginate;
    private boolean loading;
    private int totalPages;
    private int page = INITIAL_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMoviesPresenter = new PopularMoviesPresenterImpl(this);
        mRvMovies.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(SPACING));
        mRvMovies.setItemAnimator(new SlideInUpAnimator());
        mMoviesAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mMoviesAdapter);
        setPagination();
        mMoviesPresenter.getPopularMovies(Constants.API_KEY, INITIAL_PAGE);
    }

    @Override
    public void onGetPopularMoviesSuccess(Result result) {
        loading = false;
        totalPages = result.getTotalPages();
        mMoviesAdapter.setMovies(result);
        mIvPlaceholder.setVisibility(View.GONE);
        mPbMovies.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetPopularMoviesFailure(Throwable throwable) {
        loading = false;
        showFailurePlaceholder();
    }

    @Override
    public void onShowNetworkConnectionError() {
        loading = false;
        showNetworkFailurePlaceholder();
    }

    @Override
    public void onLoadMore() {
        loading = true;
        page = page + 1;
        mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == totalPages;
    }

    private void showFailurePlaceholder() {
        mPbMovies.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_error_occured);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showNetworkFailurePlaceholder() {
        mPbMovies.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_no_internet_connection);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void setPagination() {
        if (paginate != null)
            paginate.unbind();

        paginate = Paginate.with(mRvMovies, this)
                .setLoadingTriggerThreshold(4)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new LoadingListItemCreator(mMoviesAdapter.getItemCount()))
                .build();
    }
}
