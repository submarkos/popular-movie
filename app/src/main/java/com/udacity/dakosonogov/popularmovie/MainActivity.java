package com.udacity.dakosonogov.popularmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.udacity.dakosonogov.popularmovie.adapter.MovieAdapter;
import com.udacity.dakosonogov.popularmovie.api.API;
import com.udacity.dakosonogov.popularmovie.api.RestClient;
import com.udacity.dakosonogov.popularmovie.model.AllMovies;

import com.udacity.dakosonogov.popularmovie.model.MovieItem;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements  MovieAdapter.MovieAdapterListener, SharedPreferences.OnSharedPreferenceChangeListener{



    private RecyclerView mRecyclerView;
    private List<MovieItem> movies = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMovies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        getSortingBy( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popular_movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int clickedMovieIndex) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        MovieItem clickedMovie = movies.get(clickedMovieIndex);
        intent.putExtra("image", clickedMovie.getPosterPath());
        intent.putExtra("title", clickedMovie.getTitle());
        intent.putExtra("release_date", clickedMovie.getReleaseDate());
        intent.putExtra("overview", clickedMovie.getOverview());
        intent.putExtra("vote", clickedMovie.getVoteAverage());
        startActivity(intent);

    }

    private void getPopularMovies() {
        RestClient restClient = new RestClient();
        API api =
                restClient.getClient().create(API.class);
        Call<AllMovies> call = api.getPopular(BuildConfig.MOVIE_DB_TOKEN);
        call.enqueue(new Callback<AllMovies>() {
            @Override
            public void onResponse(Call<AllMovies> call, Response<AllMovies> response) {
                List<MovieItem> movieItems = response.body().getResults();
                movies = movieItems;
                mRecyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movieItems, MainActivity.this));
            }

            @Override
            public void onFailure(Call<AllMovies> call, Throwable t) {
            t.printStackTrace();
            }
        });
    }

    private void getTopRatedMovies() {
        RestClient restClient = new RestClient();
        API api =
                restClient.getClient().create(API.class);
        Call<AllMovies> call = api.getTopRated(BuildConfig.MOVIE_DB_TOKEN);
        call.enqueue(new Callback<AllMovies>() {
            @Override
            public void onResponse(Call<AllMovies> call, Response<AllMovies> response) {
                List<MovieItem> movieItems = response.body().getResults();
                movies = movieItems;
                mRecyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movieItems, MainActivity.this));
            }

            @Override
            public void onFailure(Call<AllMovies> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getSortingBy();
    }

    private void getSortingBy() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String key = this.getString(R.string.pref_sort_key);
        String sortOfCollection = sharedPreferences.getString(key,"");
        if (sortOfCollection.equals(this.getString(R.string.pref_sort_popular))) {
            getPopularMovies();
        } else getTopRatedMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSortingBy();
    }
}
