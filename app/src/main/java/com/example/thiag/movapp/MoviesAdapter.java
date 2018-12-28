package com.example.thiag.movapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thiag.movapp.fragments.movieDetail;
import com.example.thiag.movapp.fragments.movieListProcurar;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> moviesList;
    private View view;
    private int id;

    public MoviesAdapter(View view){
        this.view = view;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView releaseDate;
        private TextView voteAvarage;
        private TextView idMovie;

        public ViewHolder(final View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.movieImage);
            name = (TextView) itemView.findViewById(R.id.movieName);
            releaseDate = (TextView) itemView.findViewById(R.id.releaseDate);
            voteAvarage = (TextView) itemView.findViewById(R.id.voteAverage);
            idMovie = (TextView) itemView.findViewById(R.id.idMovie);

            //Click listener utilizado para abrir os detalhes do filme
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", idMovie.getText().toString());
                    movieDetail f = new movieDetail();
                    f.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(movieView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        //Ligando as views aos atributos
        ImageView image = holder.image;
        TextView name = holder.name;
        TextView releaseDate = holder.releaseDate;
        TextView voteAvarage = holder.voteAvarage;
        TextView idMovie = holder.idMovie;

        if(movie.getMoviePhoto() != null){
            Glide.with(view).load("https://image.tmdb.org/t/p/w500" + movie.getMoviePhoto()).into(image);
        }
        name.setText(movie.getMovieName());
        releaseDate.setText(movie.getMovieReleaseDate());
        voteAvarage.setText(String.valueOf(movie.getMovieAverageVote()));
        idMovie.setText(String.valueOf(movie.getMovieId()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
