package com.sasuke.moviedb.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.view.LoadingViewHolder;

/**
 * Created by abc on 5/2/2018.
 */

public class LoadingListItemCreator implements com.paginate.recycler.LoadingListItemCreator {

    private Context mContext;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new LoadingViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_loading_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LoadingViewHolder) holder).setLoadingText(mContext.getResources().getString(R.string.loading_more));
    }
}