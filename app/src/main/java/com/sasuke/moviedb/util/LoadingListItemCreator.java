package com.sasuke.moviedb.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sasuke.moviedb.R;
import com.sasuke.moviedb.view.LoadingViewHolder;

/**
 * Created by abc on 5/2/2018.
 */

public class LoadingListItemCreator implements com.paginate.recycler.LoadingListItemCreator {

    private int mSize;
    private Context mContext;

    public LoadingListItemCreator(int size) {
        this.mSize = size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
        return new LoadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LoadingViewHolder) holder).setLoadingText(String.format(mContext.getResources().getString(R.string.total_items_loaded)
                .concat(" %d ").concat("\n").concat(mContext.getResources().getString(R.string.loading_more)), mSize));
    }
}