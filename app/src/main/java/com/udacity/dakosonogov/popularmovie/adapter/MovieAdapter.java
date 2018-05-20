package com.udacity.dakosonogov.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.dakosonogov.popularmovie.R;
import com.udacity.dakosonogov.popularmovie.model.MovieItem;

import java.util.List;

/**
 * Created by Kosonogov on 23-02-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<MovieItem> mMovies;
    private final Context mContext;
    private MovieAdapterListener mMovieAdapterListener;

    public interface MovieAdapterListener {
        void onClick (int clickedMovieIndex);
    }

    public MovieAdapter(Context mContext, List<MovieItem> movies, MovieAdapterListener movieAdapterListener) {
        this.mContext = mContext;
        mMovies = movies;
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
        MovieItem movieItem = mMovies.get(position);
        String posterUrl = "https://image.tmdb.org/t/p/w500" + movieItem.getPosterPath();
                Picasso.with(mContext)
                .load(posterUrl)
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        if (mMovies==null) return 0;
        else return mMovies.size();
    }

    void setFilms(List<MovieItem> allMovies){
        mMovies = allMovies;
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
