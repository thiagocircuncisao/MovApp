package com.example.thiag.movapp;

import java.util.List;

public interface IAccess {
    public void search(String query); //Faz uma pesquisa com base em um texto
    public void searchWithId(String queryId); //Faz a pesquisa com base no ID do filme
    public void listMoviesInTheaters(); //Lista os filmes em cartaz
    public void listMostPopularMovies(); //Lista os filmes populares
    public void listBestRatedMovies(); //Lista os filmes mais bem avaliados
}
