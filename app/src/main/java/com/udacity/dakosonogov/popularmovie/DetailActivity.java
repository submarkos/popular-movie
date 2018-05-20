package com.udacity.dakosonogov.popularmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.udacity.dakosonogov.popularmovie.adapter.ReviewAdapter;
import com.udacity.dakosonogov.popularmovie.adapter.TrailerAdapter;
import com.udacity.dakosonogov.popularmovie.api.API;
import com.udacity.dakosonogov.popularmovie.api.RestClient;
import com.udacity.dakosonogov.popularmovie.data.MovieDBHelper;
import com.udacity.dakosonogov.popularmovie.model.AllReviews;
import com.udacity.dakosonogov.popularmovie.model.AllTrailers;
import com.udacity.dakosonogov.popularmovie.model.MovieItem;
import com.udacity.dakosonogov.popularmovie.model.Review;
import com.udacity.dakosonogov.popularmovie.model.Trailer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPoster;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;
    private String mMovieId;
    private RecyclerView mRvTrailers;
    private TrailerAdapter mTrailerAdapter;
    private List<Trailer> mTrailers;
    private RecyclerView mRvReviews;
    private ReviewAdapter mReviewAdapter;
    private List<Review> mReviews;
    private MovieDBHelper mMovieDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPoster = (ImageView)findViewById(R.id.detailPoster);
        mTitle = (TextView)findViewById(R.id.topTitle);
        mReleaseDate =(TextView)findViewById(R.id.detailReleaseDate);
        mRating = (TextView)findViewById(R.id.detailRating);
        mOverview = (TextView)findViewById(R.id.detailOverview);
        mMovieDBHelper = new MovieDBHelper(DetailActivity.this);
        final MovieItem clickedMovie = getIntent().getParcelableExtra("clicked movie");
        boolean isAlreadySaved = mMovieDBHelper.isMovieFavourite(clickedMovie.getId());
        MaterialFavoriteButton favoriteButton = (MaterialFavoriteButton) findViewById(R.id.markAsFavoritButton);
        favoriteButton.setFavorite(isAlreadySaved);
        favoriteButton.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            mMovieDBHelper.addFavouriteMovie(clickedMovie);
                        } else {
                            mMovieDBHelper.removeFavouriteMovie(clickedMovie);
                        }
                    }
                }
        );


        String posterUrl = "https://image.tmdb.org/t/p/w500" + clickedMovie.getPosterPath();
        Picasso.with(getBaseContext())
                .load(posterUrl)
                .into(mPoster);
        mTitle.setText(clickedMovie.getTitle());
        Log.d("title is", clickedMovie.getTitle());
        mReleaseDate.setText(clickedMovie.getReleaseDate());
        mRating.setText(clickedMovie.getVoteAverage());
        mOverview.setText(clickedMovie.getOverview());
        mMovieId = clickedMovie.getId();

        mTrailers = new ArrayList<>();
        mTrailerAdapter = new TrailerAdapter(this, mTrailers);
        mRvTrailers = findViewById(R.id.trailersRecyclerView);
        mRvTrailers.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecor = new DividerItemDecoration(mRvTrailers.getContext(), DividerItemDecoration.VERTICAL);
        mRvTrailers.addItemDecoration(itemDecor);
        mRvTrailers.setAdapter(mTrailerAdapter);

        mReviews = new ArrayList<>();
        mReviewAdapter = new ReviewAdapter(this, mReviews);
        mRvReviews = findViewById(R.id.reviewsRecyclerView);
        mRvReviews.setLayoutManager(new LinearLayoutManager(this));
        mRvReviews.addItemDecoration(itemDecor);
        mRvReviews.setAdapter(mReviewAdapter);

        getTrailersFromJson();
        getReviewsFromJson();
    }

    private void getTrailersFromJson() {
        RestClient restClient = new RestClient();
        API api =
                restClient.getClient().create(API.class);
        Call<AllTrailers> call = api.getTrailer(mMovieId, BuildConfig.MOVIE_DB_TOKEN);
        call.enqueue(new Callback<AllTrailers>() {
            @Override
            public void onResponse(Call<AllTrailers> call, Response<AllTrailers> response) {
                List<Trailer> trailerList =response.body().getTrailers();
                mRvTrailers.setAdapter(new TrailerAdapter(getApplicationContext(),trailerList));
            }

            @Override
            public void onFailure(Call<AllTrailers> call, Throwable t) {

            }
        });
    }
    private void getReviewsFromJson() {
        RestClient restClient = new RestClient();
        API api =
                restClient.getClient().create(API.class);
        Call<AllReviews> call = api.getReview(mMovieId, BuildConfig.MOVIE_DB_TOKEN);
        call.enqueue(new Callback<AllReviews>() {
            @Override
            public void onResponse(Call<AllReviews> call, Response<AllReviews> response) {
                List<Review> reviewList =response.body().getReviews();
                mRvReviews.setAdapter(new ReviewAdapter(getApplicationContext(),reviewList));
            }

            @Override
            public void onFailure(Call<AllReviews> call, Throwable t) {

            }
        });
    }
}
