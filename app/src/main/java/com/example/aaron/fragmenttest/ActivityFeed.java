package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

        container = (ListView) getActivity().findViewById(R.id.list);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

        SwipeRefreshLayout swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Toast.makeText(getActivity(), "refreshing", Toast.LENGTH_LONG).show();
                        loadNotifications();
                    }
                }
        );


        /*Need an object to store activity feed data, will do the same thing with
        the calendar stuff too, this may also be a good way to do friends, as it will endable for easy searching

        Create an object that holds the uri for the friends profile pic and whatever data we are trying to portay and then
        set it via the   adapter
        */

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.activity_feed_row, R.id.label, values);

        container.setAdapter(adapter);
        //loadNotifications();

        for(int i = 0; i < 20; i ++) {
            //fill in data here profile picture and action taken
            RelativeLayout temp = new RelativeLayout(getActivity());
            container.addView(temp);
            temp.setBackgroundResource(R.drawable.roundedlayout);
            LinearLayout.LayoutParams tempParams = (LinearLayout.LayoutParams) temp.getLayoutParams();
            tempParams.height = tempParams.WRAP_CONTENT;
            tempParams.width = p.x * 15 / 16;
            tempParams.topMargin = p.y * 1 / 100;
            tempParams.leftMargin = p.x / 32;
            ImageView img = new ImageView(getActivity());
            temp.addView(img);
            RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
            imgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            imgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imgParams.leftMargin = p.x / 32;
            img.setBackgroundResource(R.mipmap.ic_launcher);
            img.setId(View.generateViewId());
            TextView tv = new TextView(getActivity());
            temp.addView(tv);
            RelativeLayout.LayoutParams tvParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            tvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            tvParams.addRule(RelativeLayout.RIGHT_OF, img.getId());
            tv.setGravity(Gravity.CENTER);
            tvParams.height = tvParams.WRAP_CONTENT;
            tvParams.width = p.x *11 / 16;
            tv.setBackgroundResource(R.drawable.bluerounded);
            tv.setTextColor(Color.WHITE);
            tv.setText("Aaron Mcanerney has agreed to something");
            temp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ViewGroup parentView = (ViewGroup) view.getParent();
                    parentView.removeView(view);
                }
            });
        }

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
                for (DataSnapshot notification : snapshot.getChildren()) {
                    String text = notification.child("text").getValue(String.class);
                    buildNotification(text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void buildNotification(String text) {
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        RelativeLayout temp = new RelativeLayout(getActivity());
        container.addView(temp);
        temp.setBackgroundResource(R.drawable.roundedlayout);
        LinearLayout.LayoutParams tempParams = (LinearLayout.LayoutParams) temp.getLayoutParams();
        tempParams.height = tempParams.WRAP_CONTENT;
        tempParams.width = p.x * 15 / 16;
        tempParams.topMargin = p.y * 1 / 100;
        tempParams.leftMargin = p.x / 32;

        ImageView img = new ImageView(getActivity());
        temp.addView(img);
        RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
        imgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        imgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imgParams.leftMargin = p.x / 32;
        img.setBackgroundResource(R.mipmap.ic_launcher);
        img.setId(View.generateViewId());

        TextView tv = new TextView(getActivity());
        temp.addView(tv, 0);
        RelativeLayout.LayoutParams tvParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        tvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvParams.addRule(RelativeLayout.RIGHT_OF, img.getId());
        tv.setGravity(Gravity.CENTER);
        tvParams.height = tvParams.WRAP_CONTENT;
        tvParams.width = p.x *11 / 16;
        tv.setBackgroundResource(R.drawable.bluerounded);
        tv.setTextColor(Color.WHITE);
        tv.setText(text);

        /*temp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewGroup parentView = (ViewGroup) view.getParent();
                parentView.removeView(view);
            }
        });*/
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