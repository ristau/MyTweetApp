package com.codepath.apps.mytweetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.mytweetapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    private String tweet;
    public static String TWEET_INTENT_KEY = "tweet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient();
    }

    public void onSubmit(View v) {
        EditText etMessage = (EditText) findViewById(R.id.et_message);
        String text = etMessage.getText().toString();

        client.sendTweet(text, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                // prepare data intent
                Intent tweet = new Intent();
                Log.d("DEBUG", "SENT TWEET");
                Tweet newTweet = null;

                try {
                    newTweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DEBUG", newTweet.body);

                // pass relevant data back as a result
                tweet.putExtra(TWEET_INTENT_KEY, newTweet);

                // activity finished ok, return the data
                setResult(RESULT_OK, tweet);
                finish();
            }
        });
    }
}
