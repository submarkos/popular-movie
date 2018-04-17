package com.udacity.dakosonogov.popularmovie.utils;

import android.net.Uri;
import android.util.Log;

import com.udacity.dakosonogov.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kosonogov on 23-02-2018.
 */

public class JsonUtils {
    private static final String RESULTS = "results";
    private static final String POSTER= "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String TITLE ="title";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE = "vote_average";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private static final String SIZE = "w500";
    private static final String TAG = "MyApp";

    public static List<Movie> getAllMovies (String json) {
        try {
            JSONObject nativeMoviesJSON = new JSONObject(json);
            JSONArray allMoviesJSON = nativeMoviesJSON.getJSONArray(RESULTS);
            int numberOfMovies = allMoviesJSON.length();
            List<Movie> movies = new ArrayList<>(numberOfMovies);
            for (int i = 0; i < numberOfMovies; i++) {
                JSONObject jsonMovie = allMoviesJSON.getJSONObject(i);
                String moviePoster = jsonMovie.getString(POSTER);
                String movieOverview = jsonMovie.getString(OVERVIEW);
                String movieTitle = jsonMovie.getString(TITLE);
                String movieReleaseDate = jsonMovie.getString(RELEASE_DATE);
                String movieVote = jsonMovie.getString(VOTE);
                String moviePosterPath = new StringBuilder(moviePoster).deleteCharAt(0).toString();
                Movie movie = new Movie(fullImageUrl(moviePosterPath), movieTitle, movieOverview,movieReleaseDate,movieVote);
                movies.add(movie);

                Log.v(TAG,movie.getImage());
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    private static String fullImageUrl(String path) {
        Uri uri = Uri.parse(IMAGE_BASE_URL).buildUpon().appendPath(SIZE).appendPath(path).build();
        return uri.toString();
    }

}
