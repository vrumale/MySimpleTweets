package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.UserArrayAdapter;
import com.codepath.apps.mysimpletweets.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrumale on 3/1/2015.
 */
public class UsersListFragment extends Fragment{
    private ArrayList<User> users;
    private UserArrayAdapter aUsers;
    private ListView lvUsers;
    private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;

    //Inflate logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list,parent,false);
        lvUsers = (ListView) view.findViewById(R.id.lvUsers);
        lvUsers.setAdapter(aUsers);
        lvUsers.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
            }
        });
      /*  swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerforUser);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aUsers.clear();
                populateTimeline();
                swipeContainer.setRefreshing(false);
            }
        });
        //Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        aUsers = new UserArrayAdapter(getActivity(), users);
        client = TwitterApplication.getRestClient();

    }
    public void addAll(List<User> users) {
        aUsers.addAll(users);
    }

    protected void populateTimeline() {
    }

    protected void customLoadMoreDataFromApi(int page) {
        return;
    }

}
