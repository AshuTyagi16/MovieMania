package com.sasuke.moviedb.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.db.MovieManiaDatabaseAdapter;
import com.sasuke.moviedb.db.MovieManiaDatabaseManager;
import com.sasuke.moviedb.model.pojo.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by abc on 5/1/2018.
 */

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_thumbnail)
    ImageView mIvThumbnail;
    @BindView(R.id.iv_add_to_favoutite)
    ImageView mIvAddToFav;

    private OnItemClickListener mOnItemClickListener;
    private int movieId;

    private MovieManiaDatabaseAdapter mDatabaseAdapter;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mDatabaseAdapter = MovieManiaDatabaseAdapter.getInstance(itemView.getContext());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(movieId);
            }
        });
    }

    @OnClick(R.id.iv_add_to_favoutite)
    public void onFavouriteClick() {
        if (MovieManiaDatabaseManager.isFavourite(mDatabaseAdapter, movieId)) {
            MovieManiaDatabaseManager.removeFromFavourites(mDatabaseAdapter, movieId);
            mIvAddToFav.setImageResource(R.drawable.ic_favorite_disabled);
        } else {
            MovieManiaDatabaseManager.addToFavourites(mDatabaseAdapter, movieId);
            mIvAddToFav.setImageResource(R.drawable.ic_favouite);
        }
    }

    public void setMovie(Movie movie) {
        movieId = movie.getId();
        Picasso.get()
                .load(Constants.IMAGE_BASE_URL.concat(movie.getPosterPath()))
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvThumbnail);
        if (MovieManiaDatabaseManager.isFavourite(mDatabaseAdapter, movieId))
            mIvAddToFav.setImageResource(R.drawable.ic_favouite);
        else
            mIvAddToFav.setImageResource(R.drawable.ic_favorite_disabled);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int movieId);
    }
}
