package com.codepath.apps.restclienttemplate;


import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {


    Context context;
    List<String> followers;


    public FollowersAdapter(Context context, List<String> followers) {
        this.context = context;
        this.followers = followers;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_follower, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //get data at position
        String follower = followers.get(position);
        //bind the tweet with view holder
        holder.bind(follower);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }
    //pass in context and list of tweets
    //for each row, inflate layoutå
    //bind values based on the position of the element- Reccycleer view will tell us position to bind in

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFollowerName;

        //itemView represents one row in the RecyclerView

        public ViewHolder(View itemView) {
            super(itemView);
            tvFollowerName = itemView.findViewById(R.id.tvFollowerName);

        }

        public void bind(final String follower) {
            tvFollowerName.setText(follower);

            //put onclicklistener in bind
        }

    }


}
