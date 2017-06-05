package com.codepath.apps.mytweetapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mytweetapp.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    // member variable for the tweets
    private List<Tweet> mTweets;

    // context for easy access
    private Context mContext;

    // pass in the Tweets array in the constructor
    public TweetAdapter(Context context, List<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    public List<Tweet> getTweets(){
        return mTweets;
    }

    private Context getContext() {
        return mContext;
    }

    // for each row, inflate the layout and cache the references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        holder.tvDate.setText(tweet.getRelativeTimeAgo(tweet.createdAt));

        Glide.with(mContext).load(tweet.user.profileImageUrl).into(holder.profileImageView);

        addListenerOnImageButton(tweet, holder.profileImageView);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {

       // public ImageView ivProfileImage;
        public ImageButton profileImageView;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findbyID lookups
            profileImageView = (ImageButton) itemView.findViewById(R.id.ivProfileImage);
            //ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }

    public void addListenerOnImageButton(final Tweet tweet, ImageButton ib) {
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the profile view
                Intent i = new Intent(mContext, ProfileActivity.class);
                i.putExtra("screen_name", tweet.user.screenName);
                i.putExtra("user_id", tweet.user.uid);
                mContext.startActivity(i);
            }
        });
    }


}
