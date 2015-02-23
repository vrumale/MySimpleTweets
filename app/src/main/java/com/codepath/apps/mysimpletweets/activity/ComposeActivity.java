package com.codepath.apps.mysimpletweets.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {
    EditText etCompose;
    TextView tvCount;
    private TwitterClient client;
    private Tweet inreply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_action_twitter_bird_logo_2012);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            inreply = getIntent().getExtras().getParcelable("tweet");
        }catch (Exception e) {
            e.printStackTrace();
        }
        etCompose = (EditText) findViewById(R.id.etCompose);
        tvCount = (TextView) findViewById(R.id.tvCharCount);
        client = TwitterApplication.getRestClient();
        if(inreply != null){
            etCompose.setText("@"+inreply.getUser().getScreenName()+" ");
            etCompose.setSelection(etCompose.getText().length());
        }
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charLeft = 140 - s.length();
                if(charLeft < 100) {
                    tvCount.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                }
                if(charLeft < 50) {
                    tvCount.setTextColor(Color.RED);

                }
                tvCount.setText(String.valueOf(charLeft));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void onSubmit(View view) {
        this.finish();
    }
    public void onComposeTweet(View view) {
        String msg = String.valueOf(etCompose.getText());
        if(msg.length() == 0) {
            Toast.makeText(this, "Empty Tweet! Cant Post", Toast.LENGTH_LONG).show();
            return;
        }
        if(inreply != null) {
            client.postReTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    Toast.makeText(ComposeActivity.this, "Posted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(ComposeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", errorResponse.toString());
                }
            }, msg, inreply.getUid());
        }
        else {
            client.postNewTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    Toast.makeText(ComposeActivity.this, "Posted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(ComposeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", errorResponse.toString());
                }
            }, msg);
        }

        this.onSubmit(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
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
