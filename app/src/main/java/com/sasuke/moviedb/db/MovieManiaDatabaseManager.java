package com.sasuke.moviedb.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by abc on 5/3/2018.
 */

public class MovieManiaDatabaseManager {

    //Table Name
    private static final String TABLE_NAME = "Favourites";

    //Table Schema
    private static final String MOVIE_ID = "movie_id";


    private static SQLiteDatabase sqLiteDatabase;

    public static void addToFavourites(MovieManiaDatabaseAdapter databaseAdapter, int movieId) {
        if (databaseAdapter != null) {
            sqLiteDatabase = databaseAdapter.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(MOVIE_ID, movieId);

            sqLiteDatabase.insert(TABLE_NAME, null, values);
        }
    }

    public static void removeFromFavourites(MovieManiaDatabaseAdapter databaseAdapter, int movieId) {
        if (databaseAdapter != null) {
            sqLiteDatabase = databaseAdapter.getWritableDatabase();

            sqLiteDatabase.delete(TABLE_NAME, MOVIE_ID + " =? ",
                    new String[]{String.valueOf(movieId)});
        }
    }

    public static boolean isFavourite(MovieManiaDatabaseAdapter databaseAdapter, int movieId) {
        boolean isFavourite = false;
        if (databaseAdapter != null) {
            sqLiteDatabase = databaseAdapter.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{MOVIE_ID}, MOVIE_ID + " =? ",
                    new String[]{String.valueOf(movieId)}, null, null, null, null);

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                isFavourite = true;
            }
            cursor.close();
        }
        return isFavourite;
    }
}
