  package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

  public class ComposeActivity extends AppCompatActivity {

      //alternative to Toast is android snack bar
    EditText etCompose;
    Button btnTweet;
    public static final int MAX_LENGTH = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        //set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //ensure proper character length
                String tweetContent = etCompose.getText().toString();
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

            }
        });

    }
}