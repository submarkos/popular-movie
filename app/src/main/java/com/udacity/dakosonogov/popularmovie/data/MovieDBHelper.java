package com.udacity.dakosonogov.popularmovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.udacity.dakosonogov.popularmovie.model.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 2;
    Context context;

    public MovieDBHelper (Context context){

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_SQL_STATEMENT = "CREATE TABLE "+
                MovieContract.MovieEntry.TABLE_NAME + " ( " +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.MovieEntry.TABLE_COLUMN_TITLE + " VARCHAR NOT NULL, " +
                MovieContract.MovieEntry.TABLE_COLUMN_POSTER_PATH + " TEXT , " +
                MovieContract.MovieEntry.TABLE_COLUMN_OVERVIEW + " TEXT, " +
                MovieContract.MovieEntry.TABLE_COLUMN_RELEASE_DATE + " TEXT, " +
                MovieContract.MovieEntry.TABLE_COLUMN_VOTE_AVG + " TEXT " +
                " ) ;";
        db.execSQL(CREATE_TABLE_SQL_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean isMovieFavourite(String movieId){
        Cursor mCursor = this.context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry._ID + " = ?",
                new String[]{movieId},
                null);

        return mCursor.getCount() > 0;
    }

    public void addFavouriteMovie(MovieItem movieItem) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry._ID, movieItem.getId());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_TITLE, movieItem.getTitle());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_POSTER_PATH, movieItem.getPosterPath());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_RELEASE_DATE, movieItem.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_OVERVIEW, movieItem.getOverview());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_VOTE_AVG, movieItem.getVoteAverage());
        Uri uri = this.context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

    }

    public void removeFavouriteMovie(MovieItem movieItem) {
        int result = this.context.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry._ID + " = ?",
                new String[]{movieItem.getId()}
        );
    }
    public List<MovieItem> getFavouriteMovies() {

        List<MovieItem> favoriteMovies = new ArrayList<>();
        Cursor cursor = this.context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String movieId =cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_TITLE));
            String posterPath=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_POSTER_PATH));
            String releaseDate=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_RELEASE_DATE));
            String voteAvg = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_VOTE_AVG));
            String overview=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_OVERVIEW));
            MovieItem movieItem = new MovieItem(movieId,title,posterPath,releaseDate,voteAvg,overview);
            favoriteMovies.add(movieItem);
        }
        return favoriteMovies;

    }
}
