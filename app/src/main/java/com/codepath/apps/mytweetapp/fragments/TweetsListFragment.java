package com.codepath.apps.mytweetapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytweetapp.R;
import com.codepath.apps.mytweetapp.TweetAdapter;
import com.codepath.apps.mytweetapp.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import static com.codepath.apps.mytweetapp.R.id.rvTweet;

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetAdapter adapter;
    private RecyclerView rvTweets;



    // inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        // Lookup recycler view in activity layout
        rvTweets = (RecyclerView) v.findViewById(rvTweet);

        // attach to adapter to recycler view
        rvTweets.setAdapter(adapter);

        // set the layout manager to position the items
        rvTweets.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    // creation lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create new array list
        tweets = new ArrayList<>();

        //create adapter passing in the data
        adapter = new TweetAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweetlist) {
        tweets.addAll(tweetlist);
        adapter.notifyDataSetChanged();
    }

    public void insertTweetAtTop(Tweet tweet) {
        tweets.add(0, tweet);
        adapter.notifyItemInserted(0);
        rvTweets.getLayoutManager().scrollToPosition(0);
    }
}
