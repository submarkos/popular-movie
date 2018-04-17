package com.udacity.dakosonogov.popularmovie.model;

/**
 * Created by Kosonogov on 23-02-2018.
 */

public class Movie {

    private String image;
    private String title;
    private String overview;
    private String releaseDate;
    private String vote;

    public Movie() {

    }

    public Movie(String image) {
        this.image = image;
    }

    public Movie(String image, String title, String overview, String releaseDate, String vote) {
        this.image = image;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.vote = vote;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
