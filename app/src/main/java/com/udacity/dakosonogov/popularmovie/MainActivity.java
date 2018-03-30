package com.udacity.dakosonogov.popularmovie;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.Loader;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.udacity.dakosonogov.popularmovie.model.Movie;
import com.udacity.dakosonogov.popularmovie.utils.JsonUtils;
import com.udacity.dakosonogov.popularmovie.utils.QueryUtils;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private final int LOADER_ID = 564;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMovies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(movieAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
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
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new FetchAsyncTask(this);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        movieAdapter.setFilms(data);
    }



    @Override
    public void onLoaderReset(Loader loader) {
        movieAdapter.setFilms(null);
    }
    private static class FetchAsyncTask extends AsyncTaskLoader<List<Movie>> {
         FetchAsyncTask(Context context) {
            super(context);
        }
        List<Movie> mMovies = null;
        @Override
        public List<Movie> loadInBackground() {
            try {
                String response = QueryUtils.getHttpResponse(QueryUtils.getMovies(POPULAR));
                return JsonUtils.getAllMovies(response);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
