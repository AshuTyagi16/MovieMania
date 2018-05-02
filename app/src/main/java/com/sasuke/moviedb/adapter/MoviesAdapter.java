package com.sasuke.moviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.model.pojo.Movie;
import com.sasuke.moviedb.model.pojo.Result;
import com.sasuke.moviedb.view.MoviesViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 5/1/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> implements MoviesViewHolder.OnItemClickListener {

    private List<Movie> mMovieList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.setMovie(mMovieList.get(position));
        holder.setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return this.mMovieList == null ? 0 : this.mMovieList.size();
    }

    public void setMovies(Result result) {
        if (result.getMovies() != null)
            this.mMovieList.addAll(result.getMovies());
        notifyDataSetChanged();
    }

    public void updateAllMovies(Result result) {
        if (result.getMovies() != null) {
            this.mMovieList.clear();
            this.mMovieList.addAll(result.getMovies());
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(int movieId) {
        mOnItemClickListener.onItemClick(movieId);
    }

    public interface OnItemClickListener {
        void onItemClick(int movieId);
    }
}
