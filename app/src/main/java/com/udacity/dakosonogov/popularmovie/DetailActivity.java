package com.udacity.dakosonogov.popularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title;
    private TextView releaseDate;
    private TextView rating;
    private TextView overview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        poster = (ImageView)findViewById(R.id.detail_poster);
        title = (TextView)findViewById(R.id.detail_title);
        releaseDate =(TextView)findViewById(R.id.detail_release_date);
        rating = (TextView)findViewById(R.id.detail_rating);
        overview = (TextView)findViewById(R.id.detail_overview);

        Intent intent = getIntent();

        if(intent.hasExtra("image")){
            Picasso.with(getBaseContext()).
                    load(intent.getStringExtra("image")).
                    into(poster);
        }
        if(intent.hasExtra("title")){
            title.setText(intent.getStringExtra("title"));
        }
        if(intent.hasExtra("release_date")){
           releaseDate.setText("Release date: "+intent.getStringExtra("release_date"));
        }
        if(intent.hasExtra("overview")){
           overview.setText( intent.getStringExtra("overview"));
        }
        if(intent.hasExtra("vote")){
            rating.setText("Rating: "+intent.getStringExtra("vote"));
        }
    }
}
