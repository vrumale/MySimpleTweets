package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vrumale on 2/17/2015.
 */
public class User implements Parcelable {
    //list the attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followingCount;
    private int followersCount;
    private int statusesCount;
    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }


    public String getTagline() {
        return tagline;
    }

    public int getFriendsCount() {
        return followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }
    public int getTweetsCount() {
        return statusesCount;
    }

    //deserialize the user json => User
    public static User fromJSON(JSONObject json) {
        User u = new User();
        //Extract and fill the values
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingCount = json.getInt("friends_count");
            u.statusesCount = json.getInt("statuses_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
    public static ArrayList<User> fromJSONArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<>();
        //Iterate the json array and create users
        for (int i = 0; i< jsonArray.length(); i++) {
            try {
                JSONObject userJson = jsonArray.getJSONObject(i);
                User user = User.fromJSON(userJson);
                if(user != null) {
                    users.add(user);
                    /*if((user.uid < least_id) || ( i == 0))
                        least_id = tweet.uid;*/
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageUrl);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingCount);
        dest.writeInt(this.statusesCount);
    }

    public User() {
    }

    private User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readLong();
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
        this.followingCount = in.readInt();
        this.followersCount = in.readInt();
        this.statusesCount = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


}
