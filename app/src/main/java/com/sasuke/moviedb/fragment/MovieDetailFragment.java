package com.sasuke.moviedb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.MovieDetailPresenterImpl;
import com.sasuke.moviedb.model.pojo.MovieDetail;
import com.sasuke.moviedb.presenter.MovieDetailPresenter;
import com.sasuke.moviedb.view.MovieDetailView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

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

    private int mMovieId;

    private static final String EXTRA_MOVIE_ID = "movie_id";

    private MovieDetailPresenter mMovieDetailPresenter;

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
        if (getArguments() != null)
            mMovieId = getArguments().getInt(EXTRA_MOVIE_ID);
        mMovieDetailPresenter = new MovieDetailPresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onNetworkAvailable() {
        mMovieDetailPresenter.getMovieDetail(Constants.API_KEY, mMovieId);
    }

    @Override
    public void onNetworkFailed() {

    }

    @Override
    public void onGetMovieDetailSuccess(MovieDetail movieDetail) {
        if (movieDetail != null) {
            mTvMovieName.setText(movieDetail.getTitle());
            Picasso.get()
                    .load(Constants.IMAGE_BASE_URL.concat(movieDetail.getPosterPath()))
                    .placeholder(R.drawable.placeholder_image_loading)
                    .error(R.drawable.placeholder_error_occured)
                    .into(mIvThumbnail);
            mTvOverview.setText(movieDetail.getOverview());
            mTvRating.setText(String.valueOf(movieDetail.getVoteCount()).concat("/").concat("10"));
            mTvTotalTime.setText(String.valueOf(movieDetail.getRuntime()));
            mTvYear.setText(movieDetail.getReleaseDate());
        }
    }

    @Override
    public void onGetMovieDetailFailure(Throwable throwable) {

    }
}
