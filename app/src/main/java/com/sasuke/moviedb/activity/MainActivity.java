package com.sasuke.moviedb.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.paginate.Paginate;
import com.sasuke.moviedb.R;
import com.sasuke.moviedb.adapter.MoviesAdapter;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.event.NetworkChangedEvent;
import com.sasuke.moviedb.model.MoviesPresenterImpl;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.presenter.MoviesPresenter;
import com.sasuke.moviedb.util.ItemDecorator;
import com.sasuke.moviedb.util.LoadingListItemCreator;
import com.sasuke.moviedb.util.NetworkChangeReceiver;
import com.sasuke.moviedb.view.MoviesView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends BaseActivity implements MoviesView, Paginate.Callbacks, MoviesAdapter.OnItemClickListener {

    @BindView(R.id.rl)
    RelativeLayout mRlView;
    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;
    @BindView(R.id.pb_movies)
    CircularProgressBar mPbMovies;
    @BindView(R.id.iv_placeholder)
    ImageView mIvPlaceholder;

    private MoviesPresenter mMoviesPresenter;

    private static final int INITIAL_PAGE = 1;
    private static final int SPAN_COUNT = 2;
    private static final int SPACING = 0;

    private MoviesAdapter mMoviesAdapter;

    private Paginate paginate;
    private boolean loading = false;
    private int totalPages;
    private int page = INITIAL_PAGE;
    private boolean isFirstAttempt = true;

    private boolean isNetworkAvailable;

    private NetworkChangeReceiver mReciever;

    private SORT_ORDER mSortOrder = SORT_ORDER.POPULAR;
    private SORT_ORDER mLastSortOrder = mSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMoviesPresenter = new MoviesPresenterImpl(this);
        mRvMovies.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(SPACING));
        mRvMovies.setItemAnimator(new SlideInLeftAnimator());
        mMoviesAdapter = new MoviesAdapter();
        mMoviesAdapter.setOnItemClickListener(MainActivity.this);
        mRvMovies.setAdapter(mMoviesAdapter);
        setPagination();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onNetworkAvailable() {
        if (mSortOrder == SORT_ORDER.POPULAR)
            mMoviesPresenter.getPopularMovies(Constants.API_KEY, INITIAL_PAGE);
        else if (mSortOrder == SORT_ORDER.TOP_RATED)
            mMoviesPresenter.getTopRatedMovies(Constants.API_KEY, INITIAL_PAGE);
    }

    @Override
    public void onNetworkFailed() {
        loading = false;
        showNetworkFailurePlaceholder();
    }

    @Override
    public void onGetPopularMoviesSuccess(Result result) {
        handleSuccess(result);
        mLastSortOrder = mSortOrder;
    }

    @Override
    public void onGetPopularMoviesFailure(Throwable throwable) {
        handleFailure(throwable);
    }

    @Override
    public void onGetTopRatedSuccess(Result result) {
        handleSuccess(result);
        mLastSortOrder = mSortOrder;
    }

    @Override
    public void onGetTopRatedFailure(Throwable throwable) {
        handleFailure(throwable);
    }

    @Override
    public void onLoadMore() {
        loading = true;
        if (!isFirstAttempt) {
            if (mSortOrder == SORT_ORDER.POPULAR)
                mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
            else if (mSortOrder == SORT_ORDER.TOP_RATED)
                mMoviesPresenter.getTopRatedMovies(Constants.API_KEY, page);
        }
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == totalPages;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReciever = new NetworkChangeReceiver();
        registerReceiver(mReciever, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReciever);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_popular:
                if (mLastSortOrder != SORT_ORDER.POPULAR) {
                    mSortOrder = SORT_ORDER.POPULAR;
                    page = INITIAL_PAGE;
                    showInitialLoadingPlaceholder();
                    mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
                }
                return true;
            case R.id.menu_sort_top_rated:
                if (mLastSortOrder != SORT_ORDER.TOP_RATED) {
                    mSortOrder = SORT_ORDER.TOP_RATED;
                    page = INITIAL_PAGE;
                    showInitialLoadingPlaceholder();
                    mMoviesPresenter.getTopRatedMovies(Constants.API_KEY, page);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetworkChangedEvent(NetworkChangedEvent event) {
        isNetworkAvailable = event.isNetworkAvailable;
        if (!isNetworkAvailable) {
            showSnackbar(mRlView);
        } else {
            if (isFirstAttempt) {
                mIvPlaceholder.setVisibility(View.GONE);
                mPbMovies.setVisibility(View.VISIBLE);
            }
            if (mSortOrder == SORT_ORDER.POPULAR)
                mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
            else if (mSortOrder == SORT_ORDER.TOP_RATED)
                mMoviesPresenter.getTopRatedMovies(Constants.API_KEY, page);
            dismissSnackbar();
        }
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

    private void showInitialLoadingPlaceholder() {
        mIvPlaceholder.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.GONE);
        mPbMovies.setVisibility(View.VISIBLE);
    }

    private void setPagination() {
        if (paginate != null)
            paginate.unbind();

        paginate = Paginate.with(mRvMovies, this)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new LoadingListItemCreator())
                .build();
    }

    private void handleFailure(Throwable throwable) {
        loading = false;
        if (!isNetworkAvailable) {
            if (isFirstAttempt) {
                showSnackbar(mRlView);
                showNetworkFailurePlaceholder();
            } else {
                showSnackbar(mRlView);
            }
        } else {
            if (isFirstAttempt)
                showFailurePlaceholder();
            Toast.makeText(this, getString(R.string.failed).concat(" ").concat(throwable.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSuccess(Result result) {
        dismissSnackbar();
        isFirstAttempt = false;
        loading = false;
        page++;
        if (result != null) {
            totalPages = result.getTotalPages();

            if (mLastSortOrder == mSortOrder) {
                mMoviesAdapter.setMovies(result);
            } else
                mMoviesAdapter.updateAllMovies(result);

            mIvPlaceholder.setVisibility(View.GONE);
            mPbMovies.setVisibility(View.GONE);
            mRvMovies.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int movieId) {
        startActivity(MovieDetailActivity.newIntent(this, movieId));
    }

    //On Retry Click
    @Override
    public void onSnackbarClick() {
        if (mSortOrder == MainActivity.SORT_ORDER.POPULAR)
            mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
        else if (mSortOrder == MainActivity.SORT_ORDER.TOP_RATED)
            mMoviesPresenter.getTopRatedMovies(Constants.API_KEY, page);
        dismissSnackbar();
    }

    private enum SORT_ORDER {
        POPULAR, TOP_RATED
    }
}