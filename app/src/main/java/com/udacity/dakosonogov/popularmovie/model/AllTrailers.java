package com.udacity.dakosonogov.popularmovie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTrailers {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> results = null;

    public Integer getTrailerId() {
        return id;
    }

    public void setTrailerId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return results;
    }

    public void setTrailers(List<Trailer> results) {
        this.results = results;
    }
}