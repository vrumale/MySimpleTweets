package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.activity.ProfileActivity;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vrumale on 3/1/2015.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {
    public UserArrayAdapter(Context context, List<User> users) {
        super(context, android.R.layout.simple_list_item_1, users);
    }
    //Override and setup custom template
    //ViewHolder pattern needs tobe applied to every arrayAdapter pattern you build
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1. Get the tweet
        final User user = getItem(position);
        //2. Find or inflate the template
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        //3. Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserNAME);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenNAME);

        //4. Populate dta into the subviews
        tvUserName.setText(user.getName());
        tvScreenName.setText(user.getScreenName());
        tvStatus.setText(user.getTagline());
        ivProfileImage.setImageResource(android.R.color.transparent); //clear out the old image for a recycled view
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user", user);
                //Toast.makeText(getContext(), "extra user", Toast.LENGTH_SHORT).show();
                getContext().startActivity(intent);
            }
        });
       /* tvBody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailedTweetActivity.class);
                intent.putExtra("tweet", tweet);
                getContext().startActivity(intent);
            }
        });*/
        //5. Return the view to be inserted into the list

        return convertView;
    }
}
