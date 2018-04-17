package com.udacity.dakosonogov.popularmovie;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.dakosonogov.popularmovie.model.Movie;

import java.util.List;

/**
 * Created by Kosonogov on 23-02-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> movies;
    private final Context mContext;
    private MovieAdapterListener mMovieAdapterListener;

    public interface MovieAdapterListener {
        void onClick (int clickedMovieIndex);
    }

    MovieAdapter(Context mContext, MovieAdapterListener movieAdapterListener) {
        this.mContext = mContext;
        mMovieAdapterListener = movieAdapterListener;

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForMovieItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIdForMovieItem, viewGroup, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.height = viewGroup.getMeasuredHeight()/2;
        view.setLayoutParams(lp);
        view.setFocusable(true);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);
                Picasso.with(mContext)
                .load(movie.getImage())
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        if (movies==null) return 0;
        else return movies.size();
    }

    void setFilms(List<Movie> allMovies){
        movies = allMovies;
        notifyDataSetChanged();
    }

     class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mPoster;
        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View v) {
             int clickedPosition = getAdapterPosition();
             mMovieAdapterListener.onClick(clickedPosition);
         }
     }
}
