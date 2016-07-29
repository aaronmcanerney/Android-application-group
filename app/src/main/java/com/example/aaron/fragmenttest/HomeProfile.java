package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.NoSuchElementException;


public class HomeProfile extends Fragment {

    /*  @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_profile
                , container, false);
        return v;
    }
    public static HomeProfile newInstance(String text) {

        HomeProfile f = new HomeProfile();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){
        MainActivity activity = (MainActivity) getActivity();
        ImageView backdrop = (ImageView) getActivity().findViewById(R.id.drop);
        RelativeLayout rl = (RelativeLayout) this.getActivity().findViewById(R.id.home_profile_relative);

        //Image View border
        ShapeDrawable rectShapeDrawable = new ShapeDrawable();
        Paint paint = rectShapeDrawable.getPaint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        // Set profile information

        ImageView img = new ImageView(this.getActivity());
        rl.addView(img);
        //img.setBackground(rectShapeDrawable);
        TextView nameView = (TextView) activity.findViewById(R.id.textView);
        nameView.setText(activity.displayName);
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        scaleImage(backdrop, p, p.x, p.y/2);
        scaleImage(img, p, p.x * 4 /5);
        Picasso.with(activity).load(activity.profileIMG).transform(new CircleTransform()).into(img);
        GridLayout grid = new GridLayout(this.getActivity());
        grid.setColumnCount(2);
        rl.addView(grid);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) grid.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        Button connections = new Button(this.getActivity());
        connections.setText("Connections");
        connections.setWidth(p.x/2);
        connections.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toConnections(v);
            }
        });
        grid.addView(connections);
        Button activityFeed = new Button(this.getActivity());
        activityFeed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toActivityFeed(v);
            }
        });
        activityFeed.setText("Activity Feed");
        activityFeed.setWidth(p.x/2);
        grid.addView(activityFeed);
        RelativeLayout.LayoutParams backParam = (RelativeLayout.LayoutParams) backdrop.getLayoutParams();
        backParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        backdrop.setLayoutParams(backParam);
        RelativeLayout.LayoutParams imgParam = (RelativeLayout.LayoutParams) img.getLayoutParams();
        imgParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        //((RelativeLayout.LayoutParams) img.getLayoutParams()).topMargin = p.x + p.x /3;
        RelativeLayout.LayoutParams gridParam = (RelativeLayout.LayoutParams) grid.getLayoutParams();
        gridParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        img.setId(View.generateViewId());
        RelativeLayout.LayoutParams textParam = (RelativeLayout.LayoutParams) nameView.getLayoutParams();
        textParam.addRule(RelativeLayout.BELOW, img.getId());
        textParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParam.topMargin = p.x /10;
        nameView.setLayoutParams(textParam);
        backdrop.setImageResource(R.mipmap.backgroundprofileimg);






        super.onStart();
    }

    public void alert(String title, String message) {
        MainActivity activity = (MainActivity) getActivity();
        activity.alert(title, message);
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
    private void scaleImage(ImageView view, Point point, int i) throws NoSuchElementException {

       // ImageView imageView = (ImageView) this.getActivity().findViewById(R.id.imageView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = i;
        params.height = i;
        view.setLayoutParams(params);

    }
    private void scaleImage(ImageView view, Point point, int i, int y) throws NoSuchElementException {

        // ImageView imageView = (ImageView) this.getActivity().findViewById(R.id.imageView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = i;
        params.height = y;
        view.setLayoutParams(params);

    }
    public void toConnections(View view){
        Intent intent = new Intent(this.getActivity(), Connections.class) ;
        startActivity(intent);
    }
    public void toActivityFeed(View view){
        Intent intent = new Intent(this.getActivity(), ActivityFeed1.class) ;
        startActivity(intent);
    }



}






