package com.sasuke.moviedb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.db.MovieManiaDatabaseAdapter;
import com.sasuke.moviedb.db.MovieManiaDatabaseManager;
import com.sasuke.moviedb.model.MovieDetailPresenterImpl;
import com.sasuke.moviedb.model.pojo.MovieDetail;
import com.sasuke.moviedb.presenter.MovieDetailPresenter;
import com.sasuke.moviedb.view.MovieDetailView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by abc on 5/3/2018.
 */

public class MovieDetailFragment extends BaseFragment implements MovieDetailView {

    @BindView(R.id.tv_movie_name)
    TextView mTvMovieName;
    @BindView(R.id.iv_thumbnail)
    ImageView mIvThumbnail;
    @BindView(R.id.tv_total_time)
    TextView mTvTotalTime;
    @BindView(R.id.tv_rating)
    TextView mTvRating;
    @BindView(R.id.tv_overview)
    TextView mTvOverview;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.btn_mark_as_favourite)
    Button mBtnFavourite;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.pb_movie_detail)
    ProgressBar mPbMovieDetail;
    @BindView(R.id.iv_placeholder)
    ImageView mIvPlaceholder;
    @BindView(R.id.iv_add_to_favoutite)
    ImageView mIvAddToFav;

    private int mMovieId;

    private static final String EXTRA_MOVIE_ID = "movie_id";

    private MovieDetailPresenter mMovieDetailPresenter;

    private MovieManiaDatabaseAdapter mDatabaseAdapter;

    private int movieId;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie_detail;
    }

    public static MovieDetailFragment newInstance(int movie_id) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID, movie_id);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mMovieId = getArguments().getInt(EXTRA_MOVIE_ID);
        mMovieDetailPresenter = new MovieDetailPresenterImpl(this);
        mDatabaseAdapter = MovieManiaDatabaseAdapter.getInstance(getContext());
    }

    @Override
    public void onNetworkAvailable() {
        mMovieDetailPresenter.getMovieDetail(Constants.API_KEY, mMovieId);
    }

    @Override
    public void onNetworkFailed() {
        showNetworkFailurePlaceholder();
    }

    @Override
    public void onGetMovieDetailSuccess(MovieDetail movieDetail) {
        if (movieDetail != null) {
            movieId = movieDetail.getId();
            mTvMovieName.setText(movieDetail.getTitle());

            Picasso.get()
                    .load(Constants.IMAGE_BASE_URL.concat(movieDetail.getPosterPath()))
                    .placeholder(R.drawable.placeholder_image_loading)
                    .error(R.drawable.placeholder_error_occured)
                    .into(mIvThumbnail);

            mTvOverview.setText(movieDetail.getOverview());
            mTvRating.setText(String.valueOf(movieDetail.getVoteAverage()).concat("/").concat("10"));
            mTvTotalTime.setText(String.valueOf(movieDetail.getRuntime()).concat(" ").concat("MIN"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date parse = null;
            try {
                parse = sdf.parse(movieDetail.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            mTvYear.setText(String.valueOf(c.get(Calendar.YEAR)));

            if (MovieManiaDatabaseManager.isFavourite(mDatabaseAdapter, movieDetail.getId())) {
                mIvAddToFav.setImageResource(R.drawable.ic_favouite);
                mBtnFavourite.setText(getString(R.string.remove_from_favourite));
            } else {
                mIvAddToFav.setImageResource(R.drawable.ic_favorite_disabled);
                mBtnFavourite.setText(getString(R.string.mark_as_favourite));
            }

            mPbMovieDetail.setVisibility(View.GONE);
            mIvPlaceholder.setVisibility(View.GONE);
            mRlContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetMovieDetailFailure(Throwable throwable) {
        showFailurePlaceholder();
    }

    @OnClick(R.id.btn_mark_as_favourite)
    public void onFavouriteClick() {
        if (MovieManiaDatabaseManager.isFavourite(mDatabaseAdapter, movieId)) {
            MovieManiaDatabaseManager.removeFromFavourites(mDatabaseAdapter, movieId);
            mIvAddToFav.setImageResource(R.drawable.ic_favorite_disabled);
            mBtnFavourite.setText(getString(R.string.mark_as_favourite));
        } else {
            MovieManiaDatabaseManager.addToFavourites(mDatabaseAdapter, movieId);
            mIvAddToFav.setImageResource(R.drawable.ic_favouite);
            mBtnFavourite.setText(getString(R.string.remove_from_favourite));
        }
    }

    private void showFailurePlaceholder() {
        mPbMovieDetail.setVisibility(View.GONE);
        mRlContent.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_error_occured);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showNetworkFailurePlaceholder() {
        mPbMovieDetail.setVisibility(View.GONE);
        mRlContent.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_no_internet_connection);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showInitialLoadingPlaceholder() {
        mIvPlaceholder.setVisibility(View.GONE);
        mRlContent.setVisibility(View.GONE);
        mPbMovieDetail.setVisibility(View.VISIBLE);
    }
}
