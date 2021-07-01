package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;

public class FollowersActivity extends AppCompatActivity {

    public ArrayList<String> followers;
    public ArrayList<String> following;
    public static final String TAG = "FOLLOWERS";
    FollowersAdapter followers_adapter;
    FollowingAdapter following_adapter;
    RecyclerView rvFollower;
    RecyclerView rvFollowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        rvFollower = findViewById(R.id.rvFollowers);
        rvFollowing = findViewById(R.id.rvFollowing);

        long user_id = getIntent().getLongExtra("user_id", 0);
        followers = new ArrayList<>();
        following = new ArrayList<>();


        followers_adapter = new FollowersAdapter(this, followers);
        //recycler view setup: layout manager and the adapter
        //followers_adapter.setLayoutManager(new LinearLayoutManager(this));
        rvFollower.setAdapter(followers_adapter);

        //set a Layout manager on the recycler view
        rvFollower.setLayoutManager(new LinearLayoutManager(this));

        following_adapter = new FollowingAdapter(this, following);
        //recycler view setup: layout manager and the adapter
        //followers_adapter.setLayoutManager(new LinearLayoutManager(this));
        rvFollowing.setAdapter(following_adapter);

        //set a Layout manager on the recycler view
        rvFollowing.setLayoutManager(new LinearLayoutManager(this));


        TwitterClient client = TwitterApplication.getRestClient(this);

        //changed api endpoint for getting followers
        client.getFollower(user_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray users = jsonObject.getJSONArray("users");
                    for(int i =0; i< users.length();i++){
                        followers.add(users.getJSONObject(i).getString("name"));
                    }

                    Log.i(TAG, "on success followers list: " + followers.toString());
                    //followers.addAll(Movie.fromJSONArray(results));
                    //notifies the adapter that movies list has been updated
                    followers_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i(TAG, "on success " + json.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "on fail " + response, throwable);
            }
        });

        //changed api endpoint for getting following
        client.getFollowing(user_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray users = jsonObject.getJSONArray("users");
                    for(int i =0; i< users.length();i++){
                        following.add(users.getJSONObject(i).getString("name"));
                    }

                    Log.i(TAG, "on success following list: " + following.toString());
                    following_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i(TAG, "on success " + json.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "on fail " + response, throwable);
            }
        });


    }


}