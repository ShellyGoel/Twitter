package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String embed_image;

    // empty constructor needed by the Parceler library
    public Tweet(){

    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        //getting info from different json object fields

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        //tweet.embed_image =

        //JSONArray media = jsonObject.getJSONObject("entities").getJSONArray("media");
        Log.i("TWEET","hh"+jsonObject.getJSONObject("entities"));
        if(jsonObject.getJSONObject("entities").has("media")){
            Log.i("TWEET","hh"+jsonObject.getJSONObject("entities"));
            tweet.embed_image = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
            Log.i("TWEET","embed_image "+ tweet.embed_image);
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i ++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
