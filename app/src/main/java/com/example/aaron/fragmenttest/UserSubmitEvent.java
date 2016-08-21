package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class UserSubmitEvent extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_submit_event
                , container, false);
        return v;
    }
    public static UserSubmitEvent newInstance(String text) {

        UserSubmitEvent f = new UserSubmitEvent();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);

        RelativeLayout container = (RelativeLayout) getActivity().findViewById(R.id.submit_layout);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

        RelativeLayout backdrop = new RelativeLayout(getActivity());
        container.addView(backdrop);
        backdrop.setBackgroundResource(R.drawable.roundedlayout);


        RelativeLayout.LayoutParams backdropParams = (RelativeLayout.LayoutParams) backdrop.getLayoutParams();
        backdropParams.width = p.x * 15/16;
        backdropParams.leftMargin = p.x / 32;
        backdropParams.topMargin = p.x / 32;
        backdropParams.height = backdropParams.WRAP_CONTENT;


        UserCreateEvent activity = (UserCreateEvent) getActivity();

        TextView name = new TextView(getActivity());
        name.setBackgroundResource(R.drawable.bluerounded);
        name.setTextColor(Color.WHITE);
        name.setText(activity.event.getName());
        backdrop.addView(name);
        RelativeLayout.LayoutParams nameParams = (RelativeLayout.LayoutParams) name.getLayoutParams();
        nameParams.height = nameParams.WRAP_CONTENT;
        nameParams.width = p.x *7/8;
        nameParams.topMargin = p.x/20;
        name.setId(View.generateViewId());
        nameParams.leftMargin = p.x/32;
        name.setGravity(Gravity.CENTER);

        TextView date = new TextView(getActivity());
        backdrop.addView(date);
        date.setBackgroundResource(R.drawable.bluerounded);
        date.setTextColor(Color.WHITE);
        date.setText(Utilities.formatDateAndTime(activity.event));
        RelativeLayout.LayoutParams dateParams = (RelativeLayout.LayoutParams) date.getLayoutParams();
        dateParams.addRule(RelativeLayout.BELOW, name.getId());
        dateParams.height = dateParams.WRAP_CONTENT;
        dateParams.width = p.x *7/8;
        dateParams.topMargin = p.x/20;
        date.setId(View.generateViewId());
        dateParams.leftMargin = p.x/32;
        date.setGravity(Gravity.CENTER);

        TextView where = new TextView(getActivity());
        backdrop.addView(where);
        where.setBackgroundResource(R.drawable.bluerounded);
        where.setTextColor(Color.WHITE);
        where.setText(activity.event.getPlaceName());
        RelativeLayout.LayoutParams whereParams = (RelativeLayout.LayoutParams) where.getLayoutParams();
        whereParams.addRule(RelativeLayout.BELOW, date.getId());
        whereParams.height = whereParams.WRAP_CONTENT;
        whereParams.width = p.x *7/8;
        whereParams.topMargin = p.x/20;
        where.setId(View.generateViewId());
        whereParams.leftMargin = p.x/32;
        where.setGravity(Gravity.CENTER);


        TextView description = new TextView(getActivity());
        backdrop.addView(description);
        description.setBackgroundResource(R.drawable.bluerounded);
        description.setTextColor(Color.WHITE);
        description.setText(activity.event.getDescription());
        RelativeLayout.LayoutParams descriptionParams = (RelativeLayout.LayoutParams) description.getLayoutParams();
        descriptionParams.addRule(RelativeLayout.BELOW, where.getId());
        descriptionParams.height = descriptionParams.WRAP_CONTENT;
        descriptionParams.width = p.x *7/8;
        descriptionParams.topMargin = p.x/20;
        description.setId(View.generateViewId());
        descriptionParams.leftMargin = p.x/32;
        description.setGravity(Gravity.CENTER);


        Button friends = new Button(getActivity());
        container.addView(friends);
        friends.setBackgroundResource(R.drawable.bluerounded);
        friends.setTextColor(Color.WHITE);
        friends.setText("Friends");
        RelativeLayout.LayoutParams friendsParams = (RelativeLayout.LayoutParams) friends.getLayoutParams();
        friendsParams.height = friendsParams.WRAP_CONTENT;
        friendsParams.width = p.x * 7 / 16 ;
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, description.getId());
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        friendsParams.leftMargin = p.x/32;

        Button next = new Button(getActivity());
        container.addView(next);
        next.setTextColor(Color.WHITE);
        next.setText("Submit");
        next.setBackgroundResource(R.drawable.bluerounded);
        RelativeLayout.LayoutParams nextParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        nextParams.height = p.y/10;
        nextParams.width = p.x/4;
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SubmitEvent();

                Toast.makeText(getActivity(), "Your event has been uploaded", Toast.LENGTH_LONG).show();
            }
        });

        super.onStart();
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


    public void SubmitEvent(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        for(int i = 0; i < 5; i++) {
            manager.popBackStack();
        }
        //add firebase transaction here to complete event submission
        UserCreateEvent activity = (UserCreateEvent) getActivity();
        activity.event.push();

        Intent intent  =  new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        //may need to call finish one more time here
    }
}
