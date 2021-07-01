package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;

//QUESTION: what does the extends part do again?
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {


    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NotNull
    @Override


    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view); 
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
    //get data at position
        Tweet tweet = tweets.get(position);
        //bind the tweet with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
    //pass in context and list of tweets
    //for each row, inflate layout
    //bind values based on the position of the element- Reccycleer view will tell us position to bind in

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimeStamp;
        ImageView embedImage;
        ImageView ivRetweet;
        ImageView ivLike;
        TwitterClient client;
        Long status_id;
        RecyclerView rvTweets;


        //itemView represents one row in the RecyclerView

        public ViewHolder(View itemView){
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            embedImage = itemView.findViewById(R.id.embedImage);
            ivRetweet = itemView.findViewById(R.id.ivRetweetImage);
            client = TwitterApplication.getRestClient(context);
            rvTweets = itemView.findViewById(R.id.rvFollowers);

        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            String timeStamp = getRelativeTimeAgo(tweet.createdAt);
            tvTimeStamp.setText(timeStamp);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            Glide.with(context).load(tweet.embed_image).into(embedImage);
            status_id = tweet.id;

            tvScreenName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FollowersActivity.class);
                    intent.putExtra("user_id", tweet.user.id);
                    context.startActivity(intent);
                }
            });

            ivRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    client.retweetTweet(status_id.toString(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                           // Log.i(TAG, "onSuccess to publish tweet");
                            Log.e("RETWEET", "onSuccess to retweet tweet");
                            //to parse Json object as a tweet
                            try {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                tweet.retweet = true;
                                //modify data source with tweet
                                tweets.add(0,tweet);
                                //update the recyclerView, is this needed?
                                notifyItemInserted(0);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e("RETWEET", "onFailure to retweet tweet", throwable);
                        }
                    });

                }
            });

        }

        public String getRelativeTimeAgo(String rawJsonDate) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return relativeDate;
        }
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }


}
