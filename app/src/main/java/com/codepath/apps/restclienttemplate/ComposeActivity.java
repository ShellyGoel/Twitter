  package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

  public class ComposeActivity extends AppCompatActivity {

      //alternative to Toast is android snack bar
    EditText etCompose;
    Button btnTweet;
    public static final int MAX_LENGTH = 140;
      public static final String TAG = "ComposeActivity";

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApplication.getRestClient(this);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        //set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //ensure proper character length
                final String tweetContent = etCompose.getText().toString();
                if(tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet cannot be empty!", Toast.LENGTH_SHORT).show();
                    //don't want to make an API call
                    return;
                }
                if(tweetContent.length()>MAX_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet is too long!", Toast.LENGTH_SHORT).show();
                    //don't want to make an API call
                    return;

                }

                //make an API CALL TO Twitter to publish the tweet
                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish tweet");
                        //to parse Json object as a tweet
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet says: "+tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            //set result code and bundle data for response
                            setResult(RESULT_OK,intent);
                            //closes the activity, pass data to parent
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet", throwable);
                    }
                });

                //Get request to read data
                //Post request to create data


            }
        });

    }
}