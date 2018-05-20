package com.udacity.dakosonogov.popularmovie.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String AUTHORITY = "com.udacity.dakosonogov.popularmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    private MovieContract(){}

    public static class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";
        public static final String TABLE_COLUMN_TITLE = "title";
        public static final String TABLE_COLUMN_POSTER_PATH = "poster_path";
        public static final String TABLE_COLUMN_OVERVIEW = "overview";
        public static final String TABLE_COLUMN_RELEASE_DATE = "release_date";
        public static final String TABLE_COLUMN_VOTE_AVG = "vote_avg";
    }
}
