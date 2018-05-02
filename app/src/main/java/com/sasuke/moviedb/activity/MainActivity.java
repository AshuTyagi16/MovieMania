package com.sasuke.moviedb.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.sasuke.moviedb.manager.NetworkManager;
import com.sasuke.moviedb.model.PopularMoviesPresenterImpl;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.presenter.PopularMoviesPresenter;
import com.sasuke.moviedb.util.ItemDecorator;
import com.sasuke.moviedb.util.LoadingListItemCreator;
import com.sasuke.moviedb.util.NetworkChangeReceiver;
import com.sasuke.moviedb.view.PopularMoviesView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends BaseActivity implements PopularMoviesView, Paginate.Callbacks {

    @BindView(R.id.rl)
    RelativeLayout mRlView;
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
    private boolean loading = false;
    private int totalPages;
    private int page = INITIAL_PAGE;
    private boolean isFirstAttempt = true;

    private boolean isNetworkAvailable;

    private NetworkChangeReceiver mReciever;

    private TSnackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMoviesPresenter = new PopularMoviesPresenterImpl(this);
        mRvMovies.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(SPACING));
        mRvMovies.setItemAnimator(new SlideInUpAnimator());
        mMoviesAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mMoviesAdapter);
        setPagination();
        buildSnackbar();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onNetworkAvailable() {
        mMoviesPresenter.getPopularMovies(Constants.API_KEY, INITIAL_PAGE);
    }

    @Override
    public void onNetworkFailed() {
        loading = false;
        showNetworkFailurePlaceholder();
    }

    @Override
    public void onGetPopularMoviesSuccess(Result result) {
        mSnackbar.dismiss();
        isFirstAttempt = false;
        loading = false;
        page++;
        if (result != null) {
            totalPages = result.getTotalPages();
            mMoviesAdapter.setMovies(result);
            mIvPlaceholder.setVisibility(View.GONE);
            mPbMovies.setVisibility(View.GONE);
            mRvMovies.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetPopularMoviesFailure(Throwable throwable) {
        loading = false;
        if (!isNetworkAvailable) {
            if (isFirstAttempt) {
                buildSnackbar();
                mSnackbar.show();
                showNetworkFailurePlaceholder();
            } else {
                buildSnackbar();
                mSnackbar.show();
            }
        } else {
            if (isFirstAttempt)
                showFailurePlaceholder();
            Toast.makeText(this, getString(R.string.failed).concat(" ").concat(throwable.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMore() {
        loading = true;
        if (!isFirstAttempt) {
            mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
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
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new LoadingListItemCreator())
                .build();
    }

    private void buildSnackbar() {
        mSnackbar = TSnackbar.make(mRlView, getString(R.string.no_internet_connection), TSnackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
                mSnackbar.dismiss();
            }
        });
        mSnackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white));
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetworkChangedEvent(NetworkChangedEvent event) {
        isNetworkAvailable = event.isNetworkAvailable;
        Log.d("onNetworkChangedEvent", "Network Available: >>  " + isNetworkAvailable);
        if (!isNetworkAvailable) {
            buildSnackbar();
            mSnackbar.show();
        } else {
            if (isFirstAttempt) {
                mIvPlaceholder.setVisibility(View.GONE);
                mPbMovies.setVisibility(View.VISIBLE);
            }
            mMoviesPresenter.getPopularMovies(Constants.API_KEY, page);
            mSnackbar.dismiss();
        }
    }
}