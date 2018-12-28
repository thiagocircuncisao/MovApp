package com.example.thiag.movapp;

import java.util.List;

public interface IAccess {
    public void search(String query);
    public List<Movie> returnList();
    public void listMoviesInTheaters();
    public void listMostPopularMovies();
    public void listBestRatedMovies();
}
