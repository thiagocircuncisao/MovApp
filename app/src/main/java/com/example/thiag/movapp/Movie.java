package com.example.thiag.movapp;

import android.media.Image;

public class Movie {

    private String moviePhoto;
    private String movieName;
    public String movieOverview;
    private String movieReleaseDate;
    private Double movieAverageVote;

    public Movie(String moviePhoto, String movieName, String movieReleaseDate, Double movieAverageVote) {
        this.moviePhoto = moviePhoto;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.movieAverageVote = movieAverageVote;
    }

    public Movie(){

    }

    public String getMoviePhoto() {
        return moviePhoto;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public Double getMovieAverageVote() {
        return movieAverageVote;
    }

    public void setMoviePhoto(String moviePhoto) {
        this.moviePhoto = moviePhoto;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public void setMovieAverageVote(Double movieAverageVote) {
        this.movieAverageVote = movieAverageVote;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }
}
