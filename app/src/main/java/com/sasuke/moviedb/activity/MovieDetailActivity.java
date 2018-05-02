package com.sasuke.moviedb.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.sasuke.moviedb.fragment.MovieDetailFragment;

/**
 * Created by abc on 5/3/2018.
 */

public class MovieDetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_MOVIE_ID = "movie_id";

    public static Intent newIntent(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected Fragment createFragment() {
        return MovieDetailFragment.newInstance(getIntent().getIntExtra(EXTRA_MOVIE_ID, 0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
