package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ActivityFeed extends Fragment {
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
        LinearLayout container =  (LinearLayout) getActivity().findViewById(R.id.activity_feed_layout);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

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