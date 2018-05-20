package com.udacity.dakosonogov.popularmovie.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.dakosonogov.popularmovie.R;
import com.udacity.dakosonogov.popularmovie.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private Context mContext;
    private List<Trailer> mTrailerList;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        mContext = context;
        mTrailerList = trailerList;
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutIdForTrailerItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(layoutIdForTrailerItem, viewGroup, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        holder.trailerName.setText(mTrailerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }


     class TrailerAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView trailerName;

        TrailerAdapterViewHolder (View view) {
            super(view);
            trailerName = (TextView) view.findViewById(R.id.trailerName);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Trailer clickedTrailer = mTrailerList.get(position);
                        String youtubeId = clickedTrailer.getKey();
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeId));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + youtubeId));
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
