package com.sasuke.moviedb.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sasuke.moviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 5/2/2018.
 */

public class LoadingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_loading_text)
    TextView mTvLoading;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setLoadingText(String text) {
        mTvLoading.setText(text);
    }
}
