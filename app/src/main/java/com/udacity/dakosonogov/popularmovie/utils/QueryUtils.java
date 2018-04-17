package com.udacity.dakosonogov.popularmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.udacity.dakosonogov.popularmovie.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Kosonogov on 23-02-2018.
 */

public class QueryUtils {

    private static final String MOVIES_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "api_key";
    private static final String API_PARAMETR = "";
    private static final String TAG = "MyApp";
    private static final String MOVIE_PATH = "movie";

    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            String data = null;
            if (hasInput){
                data = scanner.next();
            }
            Log.v(TAG,data);
            scanner.close();
            return data;

        }
        finally {
            connection.disconnect();
        }
    }

    public static URL getMovies (Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.pref_sort_key);
        String sortOfCollection = sharedPreferences.getString(key,"");
        Uri movieUri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(sortOfCollection)
                .appendQueryParameter(API_KEY,API_PARAMETR)
                .build();

        try{
            URL url = new URL(movieUri.toString());
            Log.v("URL",url.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}



