package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //inflates the layout from XML and returning the viewholder 
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // populates data into the item through the viewholder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder "+position);
        //#1 get the movie at the position it was passed in
        Movie movie = movies.get(position);
        //#2 bind the movie data into the view holder
        holder.bind(movie);
    }

    //returns the number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //simply connects the variables to the id of the items in the layout
    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //depending on the rotation of phone we want
            //backdrop(aka landscape photo)
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                imageUrl=movie.getBackdropPath();
            }
            else{
                //else we will want poster image
                imageUrl=movie.getPosterPath();
            }
            //loads imageUrl into frontend ivPoster
            Glide.with(context).load(imageUrl).into(ivPoster);

            //#1 register click listener on the entire row aka picture title and overview
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //#2 nagivate to a new activity on click
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show(); <-using toast to test the movie title
                    Intent i = new Intent(context, DetailActivity.class);
                    //parcels is an api that allows us to pass in a custom object
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
