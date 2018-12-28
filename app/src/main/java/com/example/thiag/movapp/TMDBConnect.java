package com.example.thiag.movapp;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thiag.movapp.fragments.movieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TMDBConnect extends AsyncTask implements IAccess {
    private String apiKey = "04e4c479e8643ffaa712726e7d2d72c9";

    private URL url;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private View view;
    private boolean searchWithIdOn; //Variável utilizada para auxiliar no processo de pesquisa por ID

    //Dois construtores, o primeiro utilizado no processo de criação das listas e
    // o outro para o processo de mostrar detalhe do filme
    public TMDBConnect(RecyclerView recyclerView, MoviesAdapter adapter){
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }

    public TMDBConnect(View v){
        this.view = v;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        {
            try {
                url = new URL((String) objects[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                return s;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        JSONObject jsonObject = null;
        try {
            if(o != null){
                jsonObject = new JSONObject((String) o);

                //Esta verificação existe para que o JSONArray não busque uma referencia inexistente
                //visto que aos pesquisar por ID, não há o retorno de um array e sim de um único item
                if(searchWithIdOn){
                    Movie movie = new Movie();

                    movie.setMovieId(jsonObject.getInt("id"));
                    movie.setMovieName(jsonObject.getString("title"));
                    movie.setMovieReleaseDate(jsonObject.getString("release_date"));
                    movie.setMovieAverageVote(jsonObject.getDouble("vote_average"));
                    movie.setMovieOverview(jsonObject.getString("overview"));
                    movie.setMoviePhoto(jsonObject.getString("poster_path"));

                    doAttachmentMovie(movie);
                }else {
                    ArrayList<Movie> movies = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Movie movie = new Movie();

                        movie.setMovieId(object.getInt("id"));
                        movie.setMovieName(object.getString("title"));
                        movie.setMovieReleaseDate(object.getString("release_date"));
                        movie.setMovieAverageVote(object.getDouble("vote_average"));
                        movie.setMovieOverview(object.getString("overview"));
                        movie.setMoviePhoto(object.getString("poster_path"));
                        movies.add(movie);
                    }
                    if (movies != null) {
                        adapter.setMoviesList(movies);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
            searchWithIdOn = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(String query) {
        this.execute("https://api.themoviedb.org/3/search/movie?api_key=04e4c479e8643ffaa712726e7d2d72c9&query=" + query);
    }

    @Override
    public void searchWithId(String queryId) {
        searchWithIdOn = true;
        this.execute("https://api.themoviedb.org/3/movie/" + queryId + "?api_key=04e4c479e8643ffaa712726e7d2d72c9&language=pt-BR" );
    }

    public void listMoviesInTheaters(){
        this.execute("https://api.themoviedb.org/3/movie/now_playing?api_key=04e4c479e8643ffaa712726e7d2d72c9&language=pt-BR&page=1");

    }

    public void listMostPopularMovies(){
        this.execute("https://api.themoviedb.org/3/movie/popular?api_key=04e4c479e8643ffaa712726e7d2d72c9&language=pt-BR&page=1");

    }

    public void listBestRatedMovies(){
        this.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=04e4c479e8643ffaa712726e7d2d72c9&language=pt-BR&page=1");

    }

    public void doAttachmentMovie(Movie movie){
        //Classe de auxilio para ligar as informações às views do movie_detail
        ImageView image = (ImageView) view.findViewById(R.id.movieImageDetail);;
        TextView name = (TextView) view.findViewById(R.id.movieNameDetail);;
        TextView releaseDate = (TextView) view.findViewById(R.id.releaseDateDetail);;
        TextView averageVote = (TextView) view.findViewById(R.id.voteAverageDetail);;
        TextView overview = (TextView) view.findViewById(R.id.overviewDetail);;

        if(movie.getMoviePhoto() != null){
            Glide.with(view).load("https://image.tmdb.org/t/p/w500" + movie.getMoviePhoto()).into(image);
        }
        name.setText(movie.getMovieName());
        releaseDate.setText(movie.getMovieReleaseDate());
        averageVote.setText(movie.getMovieAverageVote().toString());
        overview.setText(movie.getMovieOverview());
    }
}
