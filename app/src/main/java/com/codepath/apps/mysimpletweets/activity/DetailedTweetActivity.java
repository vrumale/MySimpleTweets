package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailedTweetActivity extends ActionBarActivity {
    private ImageView ivProfileImage;
    private TextView tvUserName;
    private TextView tvBody;
    private TextView tvScreenName;
    private  TextView tvCreatedTime;
    private Tweet tweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tweet);
        //Action Bar to go home
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_action_twitter_bird_logo_2012);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tweet =(Tweet) getIntent().getParcelableExtra("tweet");
        setView(tweet);
    }

    public void setView(Tweet tweet) {
        ivProfileImage = (ImageView) findViewById(R.id.ivDVProfileImage);
        tvBody = (TextView) findViewById(R.id.tvDVBody);
        tvUserName = (TextView) findViewById(R.id.tvDVUserNAME);
        tvScreenName = (TextView) findViewById(R.id.tvDVScreenNAME);
        tvBody.setText(tweet.getBody());
        tvCreatedTime = (TextView) findViewById(R.id.tvDVCreatedTime);
        tvCreatedTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        tvUserName.setText(tweet.getUser().getName());
        tvScreenName.setText(tweet.getUser().getScreenName());
        ivProfileImage.setImageResource(0);
        Picasso.with(getBaseContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

    }
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
            // Toast.makeText(this, relativeDate, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
    public void onTweetReply(View view) {
        Intent i = new Intent(DetailedTweetActivity.this,ComposeActivity.class );
        i.putExtra("tweet", tweet);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_tweet, menu);
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
}
