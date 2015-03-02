package com.codepath.apps.mysimpletweets.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.FollowingListTimeline;
import com.codepath.apps.mysimpletweets.fragments.FriendsListTimeline;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {
    TwitterClient client;
    public User user;
    public String screenName;
    public int[] count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_action_twitter_bird_logo_2012);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        count = new int[3];
        try {
            user = (User) getIntent().getParcelableExtra("user");
            if(user != null) {
              //  Toast.makeText(this, "Correct USER", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle("@" + user.getScreenName());
                populateProfileHeader(user);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        if(user == null){
            // you can make this call anywhere in the application you will get the same instance
            client = TwitterApplication.getRestClient();
            //Get the account information
            client.getUserInfo(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    //My current user account information
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                }

            });
        }
        //Get the screen name from the activity that launaches this
        if(user != null) {
            screenName = new String(user.getScreenName());
        }
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewprofilepager);
        vpPager.setAdapter(new ProfilePagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabsProfile);
        tabStrip.setViewPager(vpPager);
    }
    public void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        count[0] = user.getTweetsCount();
        count[1] = user.getFollowersCount();
        count[2] = user.getFriendsCount();
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class ProfilePagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;

        private String tabTitles[] = {"Tweets:", "Followers:", "Following:"};

        public ProfilePagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

                return fragmentUserTimeline;

            }
            else if(position == 1){
               // return new UserTimelineFragment();
                FriendsListTimeline friendsListTimeline = FriendsListTimeline.newInstance(screenName);

                return friendsListTimeline;
            }
            else if(position == 2) {
                //return new UserTimelineFragment();
                FollowingListTimeline followingListTimeline = FollowingListTimeline.newInstance(screenName);

                return followingListTimeline;
            }
            else
                return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String countStr = new String(Integer.toString(count[position]));
            String label = tabTitles[position];
            return Html.fromHtml(label +countStr);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
