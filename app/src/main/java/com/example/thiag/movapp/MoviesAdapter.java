package com.example.thiag.movapp;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> moviesList;
    private View view;

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

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.movieImage);
            name = (TextView) itemView.findViewById(R.id.movieName);
            releaseDate = (TextView) itemView.findViewById(R.id.releaseDate);
            voteAvarage = (TextView) itemView.findViewById(R.id.voteAverage);
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

        ImageView image = holder.image;
        TextView name = holder.name;
        TextView releaseDate = holder.releaseDate;
        TextView voteAvarage = holder.voteAvarage;
        if(movie.getMoviePhoto() != null){
            Glide.with(view).load("https://image.tmdb.org/t/p/w500" + movie.getMoviePhoto()).into(image);
        }
        name.setText(movie.getMovieName());
        releaseDate.setText(movie.getMovieReleaseDate());
        voteAvarage.setText(String.valueOf(movie.getMovieAverageVote()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
