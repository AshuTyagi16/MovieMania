package com.sasuke.moviedb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abc on 5/3/2018.
 */

public class MovieManiaDatabaseAdapter extends SQLiteOpenHelper {

    //Database Name
    private static final String DATABASE_NAME = "Movie_Mania";

    //Table Name
    private static final String TABLE_NAME = "Favourites";

    //Table Schema
    private static final String MOVIE_ID = "movie_id";

    //Database Version
    private static final int VERSION = 1;

    //Drop Table Command
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //Create Table Command
    private static final String DATABASE_CREATE_TABLE_FAVOURITES = "CREATE TABLE "
            + TABLE_NAME
            + "("
            + MOVIE_ID
            + " INTEGER PRIMARY KEY);";

    private MovieManiaDatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static volatile MovieManiaDatabaseAdapter instance;

    public static MovieManiaDatabaseAdapter getInstance(Context context) {
        if (instance == null) {
            synchronized (MovieManiaDatabaseAdapter.class) {
                if (instance == null) {
                    instance = new MovieManiaDatabaseAdapter(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(DATABASE_CREATE_TABLE_FAVOURITES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
