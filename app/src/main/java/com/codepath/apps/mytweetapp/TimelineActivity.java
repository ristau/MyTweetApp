package com.codepath.apps.mytweetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mytweetapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytweetapp.fragments.MentionsTimelineFragment;
import com.codepath.apps.mytweetapp.models.Tweet;

public class TimelineActivity extends AppCompatActivity {


    private SmartFragmentStatePagerAdapter adapterViewPager;
    //  private TweetsListFragment listFragment;
    //   private FragmentPagerAdapter adapterViewPager;
    private ViewPager vpPager;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting up the ViewPager
        // 1. Get the viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);

        // 2. set the viewpager adapter for the pager
        // adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        // vpPager.setAdapter(adapterViewPager);

        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // 3. find the pager sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // 4. attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                composeMessage();
                return true;
            case R.id.miProfile:
                showProfileView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void composeMessage(){
        Toast.makeText(this, "COMPOSING MESSAGE", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ComposeActivity.class);
        i.putExtra("tweetText", 2);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void showProfileView(){

        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            //String message = data.getExtras().getString("message");

            Tweet newTweet = data.getParcelableExtra(ComposeActivity.TWEET_INTENT_KEY);
            Log.d("DEBUG NEW TWEET", newTweet.body);
            // gets first Fragment item within the pager
            HomeTimelineFragment fragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);

           // HomeTimelineFragment fragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());
            fragment.insertTweetAtTop(newTweet);

            //   vpPager.setCurrentItem(0);
            //  tweets.add(0, newTweet);
           // adapter.notifyDataSetChanged();
          //  int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
          //  Toast.makeText(this, newTweet.user.name, Toast.LENGTH_SHORT).show();
        }
    }

    // Returns the order of the fragments in thew view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {

        private String tabTitle[] = { "Home", "Mentions" };

        // Adapter gets the manager insert or remove fragment from the activity

        public TweetsPagerAdapter(FragmentManager fn) {
            super(fn);  // pass through to the base class
        }

        // The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new HomeTimelineFragment();
                case 1:
                    return new MentionsTimelineFragment();
                default:
                    return null;
            }
        }

        // return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        // How many fragments there are to swipe between
        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }
}
