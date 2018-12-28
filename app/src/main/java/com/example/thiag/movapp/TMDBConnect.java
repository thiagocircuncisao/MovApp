package com.example.thiag.movapp;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;

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
    private List<Movie> moviesList;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    public TMDBConnect(RecyclerView recyclerView, MoviesAdapter adapter){
        this.recyclerView = recyclerView;
        this.adapter = adapter;
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
            jsonObject = new JSONObject((String) o);
            ArrayList<Movie> movies = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
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
            if(movies != null) {
                adapter.setMoviesList(movies);
                recyclerView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(String query) {
        this.execute("https://api.themoviedb.org/3/search/movie?api_key=04e4c479e8643ffaa712726e7d2d72c9&query=" + query);
    }

    @Override
    public List<Movie> returnList() {
        return moviesList;
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
}
