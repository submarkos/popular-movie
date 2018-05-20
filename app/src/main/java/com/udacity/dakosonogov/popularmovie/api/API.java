package com.udacity.dakosonogov.popularmovie.api;

import com.udacity.dakosonogov.popularmovie.model.AllMovies;
import com.udacity.dakosonogov.popularmovie.model.AllReviews;
import com.udacity.dakosonogov.popularmovie.model.AllTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("movie/popular")
    Call<AllMovies>getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<AllMovies>getTopRated(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<AllTrailers>getTrailer(@Path("movie_id") String id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<AllReviews>getReview(@Path("movie_id") String id, @Query("api_key") String apiKey);
}
