package com.sasuke.moviedb.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.config.Constants;
import com.sasuke.moviedb.model.pojo.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 5/1/2018.
 */

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_thumbnail)
    ImageView mIvThumbnail;

    private OnItemClickListener mOnItemClickListener;
    private int movieId;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(movieId);
            }
        });
    }

    public void setMovie(Movie movie) {
        movieId = movie.getId();
        Picasso.get()
                .load(Constants.IMAGE_BASE_URL.concat(movie.getPosterPath()))
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvThumbnail);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int movieId);
    }
}
