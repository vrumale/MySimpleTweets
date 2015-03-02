package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vrumale on 3/1/2015.
 * Todo: Infinite scrolling of list
 */
public class FriendsListTimeline extends UsersListFragment {
    private TwitterClient client;
    String screenName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        screenName = getArguments().getString("screen_name");
        populateTimeline();
    }
    public static FriendsListTimeline newInstance(String screen_name) {
        FriendsListTimeline userFragment = new FriendsListTimeline();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    // Send a Api request to gte the timeline json
    //Fill the listview by creating the tweet objects from the json
    @Override
    protected void populateTimeline() {

        client.getFriendsList(screenName, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG Success", json.toString());
                //JSON COMES HERE
                //DESERIALIZE JSON
                //CREATE MODLES
                // LOAD THE MODEL DATA INTO LISTVIEW
                try {
                  addAll(User.fromJSONArray(json.getJSONArray("users")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG Failure", errorResponse.toString());
            }
        });
    }

    @Override
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        String screenName = getArguments().getString("screen_name");
        client.getFriendsList(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //Log.d("DEBUG", json.toString());
                addAll(User.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
