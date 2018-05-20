package com.udacity.dakosonogov.popularmovie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllReviews {
    @SerializedName("results")
    @Expose
    private List<Review> results = null;

    public List<Review> getReviews() {
        return results;
    }

    public void setReviews(List<Review> results) {
        this.results = results;
    }
}
