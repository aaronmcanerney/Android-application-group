package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OnCalendarClicked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_calendar_clicked);
        Display d = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);

        RelativeLayout container = (RelativeLayout) this.findViewById(R.id.on_calendar_clicked);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

        RelativeLayout backdrop = new RelativeLayout(this);
        container.addView(backdrop);
        backdrop.setBackgroundResource(R.drawable.roundedlayout);


        RelativeLayout.LayoutParams backdropParams = (RelativeLayout.LayoutParams) backdrop.getLayoutParams();
        backdropParams.width = p.x * 15/16;
        backdropParams.leftMargin = p.x / 32;
        backdropParams.topMargin = p.x / 32;
        backdropParams.height = backdropParams.WRAP_CONTENT;




        TextView name = new TextView(this);
        name.setBackgroundResource(R.drawable.bluerounded);
        name.setTextColor(Color.WHITE);
        name.setText("name");
        backdrop.addView(name);
        RelativeLayout.LayoutParams nameParams = (RelativeLayout.LayoutParams) name.getLayoutParams();
        nameParams.height = nameParams.WRAP_CONTENT;
        nameParams.width = p.x *7/8;
        nameParams.topMargin = p.x/20;
        name.setId(View.generateViewId());
        nameParams.leftMargin = p.x/32;
        name.setGravity(Gravity.CENTER);

        TextView date = new TextView(this);
        backdrop.addView(date);
        //date.setBackgroundResource(R.drawable.bluerounded);
        //date.setTextColor(Color.WHITE);
        date.setText("date");
        RelativeLayout.LayoutParams dateParams = (RelativeLayout.LayoutParams) date.getLayoutParams();
        dateParams.addRule(RelativeLayout.BELOW, name.getId());
        dateParams.height = dateParams.WRAP_CONTENT;
        dateParams.width = p.x *7/8;
        dateParams.topMargin = p.x/20;
        date.setId(View.generateViewId());
        dateParams.leftMargin = p.x/32;
        date.setGravity(Gravity.CENTER);

        TextView where = new TextView(this);
        backdrop.addView(where);
        //where.setBackgroundResource(R.drawable.bluerounded);
        //where.setTextColor(Color.WHITE);
        where.setText("Place");
        RelativeLayout.LayoutParams whereParams = (RelativeLayout.LayoutParams) where.getLayoutParams();
        whereParams.addRule(RelativeLayout.BELOW, date.getId());
        whereParams.height = whereParams.WRAP_CONTENT;
        whereParams.width = p.x *7/8;
        whereParams.topMargin = p.x/20;
        where.setId(View.generateViewId());
        whereParams.leftMargin = p.x/32;
        where.setGravity(Gravity.CENTER);


        TextView description = new TextView(this);
        backdrop.addView(description);
        //description.setBackgroundResource(R.drawable.bluerounded);
        //description.setTextColor(Color.WHITE);
        description.setText("Description");
        RelativeLayout.LayoutParams descriptionParams = (RelativeLayout.LayoutParams) description.getLayoutParams();
        descriptionParams.addRule(RelativeLayout.BELOW, where.getId());
        descriptionParams.height = descriptionParams.WRAP_CONTENT;
        descriptionParams.width = p.x *7/8;
        descriptionParams.topMargin = p.x/20;
        description.setId(View.generateViewId());
        descriptionParams.leftMargin = p.x/32;
        description.setGravity(Gravity.CENTER);


        Button friends = new Button(this);
        container.addView(friends);
        //friends.setBackgroundResource(R.drawable.bluerounded);
        //friends.setTextColor(Color.WHITE);
        friends.setText("Friend");
        RelativeLayout.LayoutParams friendsParams = (RelativeLayout.LayoutParams) friends.getLayoutParams();
        friendsParams.height = friendsParams.WRAP_CONTENT;
        friendsParams.width = p.x * 7 / 16 ;
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, description.getId());
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        friendsParams.leftMargin = p.x/32;


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
