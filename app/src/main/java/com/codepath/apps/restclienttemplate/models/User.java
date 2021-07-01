package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.Entity;

import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

import okhttp3.Headers;

@Parcel
@Entity
public class User {

    public long id;
    public String name;
    public String screenName;
    public String profileImageUrl;

    //public JSONObject following;


    public User() {
    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.id = jsonObject.getLong("id");

//
//        user.followers = jsonObject.getJSONObject("
//        //user.following = jsonObject.getJSONObject("friends/list");
        return user;

    }
}
