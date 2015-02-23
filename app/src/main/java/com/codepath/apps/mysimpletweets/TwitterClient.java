package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import static com.codepath.apps.mysimpletweets.models.Tweet.getLeast_id;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "PSiiKqaCdH8S3cSgn5nl8hm6a";       // Change this
	public static final String REST_CONSUMER_SECRET = "CF0k6wYusf5D68MEYX6dhkeli0qXH8cIiY6ltqOGub2Hx7LtZo"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}
    // each method = endpoint
    // eXAMPLE hometimeline

    /**
     * EndPoints:
     - Home time line for the user
     GET https://api.twitter.com/1.1/statuses/home_timeline.json
     ○ Count = 25
     ○ Since_id =1 limiting which tweet you get( 1 means all tweets)

     * @param handler
     */
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //Specify the params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        //Execute the request
        getClient().get(apiUrl, params, handler);
    }
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
    /* Get hometime for subsequent request*/
    public void getHomeTimelineNext(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //Specify the params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        params.put("max_id", getLeast_id()-1);
        //Execute the request
        getClient().get(apiUrl, params, handler);
    }
    /** Send a Tweet
      * Resource URL : https://api.twitter.com/1.1/statuses/update.json
      * parameter: status
     * Example : https://api.twitter.com/1.1/statuses/update.json?status=Maybe%20he%27ll%20finally%20find%20his%20keys.%20%23peterfalk

     **/
    public void postNewTweet(AsyncHttpResponseHandler handler, String msg) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", msg);
        //Execute request using POST
        RequestHandle responseHandler= getClient().post(apiUrl, params, handler);
        if(responseHandler.isFinished()){
            Log.d("post", "success");
        }
    }

    /**
     *
     * @param handler
     * @param msg
     * @param status_id : in_reply_to_status_id
     */
    public void postReTweet(AsyncHttpResponseHandler handler, String msg, Long status_id) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", msg);
        params.put("in_reply_to_status_id", status_id);
        //Execute request using POST
        RequestHandle responseHandler= getClient().post(apiUrl, params, handler);
        if(responseHandler.isFinished()){
            Log.d("post", "success");
        }
    }
}