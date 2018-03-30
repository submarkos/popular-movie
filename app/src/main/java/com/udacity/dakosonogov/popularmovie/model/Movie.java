package com.udacity.dakosonogov.popularmovie.model;

/**
 * Created by Kosonogov on 23-02-2018.
 */

public class Movie {

    private String image;

    public Movie() {

    }

    public Movie(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
