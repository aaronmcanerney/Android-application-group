package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
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
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        MainActivity activity = (MainActivity) getActivity();

        RelativeLayout container = (RelativeLayout) this.getActivity().findViewById(R.id.home_profile_relative);

        container.setBackgroundColor(Color.parseColor("#d6dbe1"));
        RelativeLayout profileContent = new RelativeLayout(getActivity());
        container.addView(profileContent);
        profileContent.getLayoutParams().height = p.y/3;
        profileContent.getLayoutParams().width = p.x - 40;
        profileContent.setId(View.generateViewId());
        profileContent.setBackgroundResource(R.drawable.roundedlayout);
        ((RelativeLayout.LayoutParams) profileContent.getLayoutParams()).leftMargin = 20;
        ((RelativeLayout.LayoutParams) profileContent.getLayoutParams()).rightMargin = 20;


        ImageView profilePicture = new ImageView(getActivity());
        profilePicture.setId(View.generateViewId());
        profileContent.addView(profilePicture);
        scaleImage(profilePicture, p, p.y / 4);
        profilePicture.setId(View.generateViewId());
        Picasso.with(activity).load(activity.profileIMG).transform(new CircleTransform()).into(profilePicture);
        RelativeLayout.LayoutParams picture = (RelativeLayout.LayoutParams) profilePicture.getLayoutParams();
        picture.addRule(RelativeLayout.CENTER_IN_PARENT);
        picture.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        profilePicture.setLayoutParams(picture);
        ((RelativeLayout.LayoutParams) profilePicture.getLayoutParams()).leftMargin = 20;


        TextView userInfo = new TextView(getActivity());
        profileContent.addView(userInfo);
        userInfo.setLines(2);
        userInfo.setText("Aaron Mcanerney \n Santa Cruz Calfornia");
        userInfo.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams infoParams = (RelativeLayout.LayoutParams) userInfo.getLayoutParams();
        infoParams.addRule(RelativeLayout.RIGHT_OF, profilePicture.getId());
        infoParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        infoParams.height = p.y / 10;
        infoParams.width = p.x /2 - 10;
        infoParams.leftMargin = 10;
        userInfo.setGravity(Gravity.CENTER);
        userInfo.setBackgroundResource(R.drawable.bluerounded);


        TextView tagContainer = new TextView(getActivity());
        container.addView(tagContainer);
        tagContainer.getLayoutParams().height = p.y / 5;
        tagContainer.getLayoutParams().width = p.x *2/3;
        RelativeLayout.LayoutParams tagParams = (RelativeLayout.LayoutParams) tagContainer.getLayoutParams();
        tagParams.addRule(RelativeLayout.BELOW, profileContent.getId());
        tagParams.topMargin = 50;
        tagParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tagParams.leftMargin = 20;
        tagContainer.setLayoutParams(tagParams);
        tagContainer.setBackgroundResource(R.drawable.bluerounded);
        tagContainer.setId(View.generateViewId());



        Button edit = new Button(getActivity());
        container.addView(edit);
        edit.setText("edit");
        edit.setTextColor(Color.parseColor("#3fa9f5"));
        RelativeLayout.LayoutParams editParams = (RelativeLayout.LayoutParams) edit.getLayoutParams();
        editParams.addRule(RelativeLayout.RIGHT_OF, tagContainer.getId());
        editParams.addRule(RelativeLayout.ALIGN_BOTTOM, tagContainer.getId());
        editParams.leftMargin = 20;
        edit.setBackgroundResource(R.drawable.roundedlayout);

        RelativeLayout friends = new RelativeLayout(getActivity());
        container.addView(friends);
        friends.setBackgroundResource(R.drawable.roundedlayout);
        friends.getLayoutParams().height = p.y /4;
        friends.getLayoutParams().width = p.x / 18 * 8;
        RelativeLayout.LayoutParams friendsParams = (RelativeLayout.LayoutParams) friends.getLayoutParams();
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        friendsParams.leftMargin = 20;
        friends.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toConnections(v);
            }
        });



        RelativeLayout addEvent = new RelativeLayout(getActivity());
        container.addView(addEvent);
        addEvent.setBackgroundResource(R.drawable.roundedlayout);
        addEvent.getLayoutParams().height = p.y /4;
        addEvent.getLayoutParams().width = p.x / 18 * 8;
        RelativeLayout.LayoutParams eventParams = (RelativeLayout.LayoutParams) addEvent.getLayoutParams();
        eventParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        eventParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        eventParams.rightMargin = 20;

        /*
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
        scaleImage(img, p, p.x * 3/5);
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




        */

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






