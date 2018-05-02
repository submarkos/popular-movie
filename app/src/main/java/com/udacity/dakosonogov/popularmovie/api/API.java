package com.udacity.dakosonogov.popularmovie.api;

import com.udacity.dakosonogov.popularmovie.model.AllMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("movie/popular")
    Call<AllMovies>getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<AllMovies>getTopRated(@Query("api_key") String apiKey);
}
