package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vrumale on 2/17/2015.
 *  "text": "just another test",
     "contributors": null,
     "id": 240558470661799936,
     "retweet_count": 0,
     "in_reply_to_status_id_str": null,
     "geo": null,
     "retweeted": false,
     "in_reply_to_user_id": null,
     "place": null,
     "source": "<a href="//realitytechnicians.com\"" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
     "user": {
       "name": "OAuth Dancer",
       "profile_sidebar_fill_color": "DDEEF6",
       "profile_background_tile": true,
       "profile_sidebar_border_color": "C0DEED",
       "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
       "created_at": "Wed Mar 03 19:37:35 +0000 2010",
       "location": "San Francisco, CA",
       "follow_request_sent": false,
       "id_str": "119476949",
       "is_translator": false,
       "profile_link_color": "0084B4",
       "entities": {
         "url": {
           "urls": [
             {
               "expanded_url": null,
               "url": "http://bit.ly/oauth-dancer",
               "indices": [
                 0,
                 26
               ],
               "display_url": null
             }
           ]
         },
         "description": null
       },

 */
//Parse the JSON + Store
public class Tweet implements Parcelable {
    //List out the attributes
    private  String body;
    private long uid; // unique id for the tweet
    private User user;
    private String createdAt;
    private static long least_id; //Least id so far

    //Deserialize the JSON and build tweet objects

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }
    public static long getLeast_id () { return least_id;}
    public void setLeast_id(long id) { least_id = id;}

    public String getCreatedAt() {
        return createdAt;
    }
    public User getUser() {
        return user;
    }
    //Tweet.fromJSON("{ ... }") => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        //Extract the values from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        //Return the tweet object
        return tweet;
    }
    //Tweet.fromJSONArray([ {...}, {...}} => List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //Iterate the json array and create tweets
        for (int i = 0; i< jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null) {
                    tweets.add(tweet);
                    if((tweet.uid < least_id) || ( i == 0))
                        least_id = tweet.uid;
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.uid);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.createdAt);
    }

    public Tweet() {
    }

    private Tweet(Parcel in) {
        this.body = in.readString();
        this.uid = in.readLong();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.createdAt = in.readString();
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
