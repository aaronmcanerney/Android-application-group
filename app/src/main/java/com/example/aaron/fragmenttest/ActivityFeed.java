package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ActivityFeed extends Fragment {
    private ListView container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activity_feed
                , container, false);
        return v;
    }
    public static ActivityFeed newInstance(String text) {

        ActivityFeed f = new ActivityFeed();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


    public void onStart(){
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);

        container = (ListView) getActivity().findViewById(R.id.activity_list);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.activity_feed_swipe_refresh);
        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Toast.makeText(getActivity(), "refreshing", Toast.LENGTH_LONG).show();
                        loadNotifications();
                        swipe.setRefreshing(false);
                    }
                }
        );


        /*Need an object to store activity feed data, will do the same thing with
        the calendar stuff too, this may also be a good way to do friends, as it will endable for easy searching

        Create an object that holds the uri for the friends profile pic and whatever data we are trying to portay and then
        set it via the   adapter
        */




        loadNotifications();



        super.onStart();
    }


    public void loadNotifications() {
        MainActivity activity = (MainActivity) getActivity();
        FirebaseUser user = activity.getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference notifications = database.child("notifications").child(user.getUid());
        notifications.limitToLast(20).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> notifications = new ArrayList<>();
                for (DataSnapshot notification : snapshot.getChildren()) {
                    String text = notification.child("text").getValue(String.class);
                    notifications.add(0, text);
                }

                // Populate notifications
                container.setAdapter(new ActivityFeedAdapter(getActivity(), notifications));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }
}