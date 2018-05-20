package com.udacity.dakosonogov.popularmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.dakosonogov.popularmovie.R;
import com.udacity.dakosonogov.popularmovie.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>  {

    private Context mContext;
    private List<Review> mReviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        mContext = context;
        mReviewList = reviewList;
    }


    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutIdForReviewItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(layoutIdForReviewItem, viewGroup, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {
        holder.reviewAuthor.setText("by " + mReviewList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView reviewAuthor;

        ReviewAdapterViewHolder(View view) {
            super(view);
            reviewAuthor = (TextView)view.findViewById(R.id.reviewAuthor);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Review clickedReview = mReviewList.get(position);
                        String reviewLink = clickedReview.getUrl();

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewLink));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
