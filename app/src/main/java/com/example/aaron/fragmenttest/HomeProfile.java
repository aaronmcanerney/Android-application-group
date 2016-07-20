package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.NoSuchElementException;


public class HomeProfile extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

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

        // Set profile information
        ImageView img = (ImageView) activity.findViewById(R.id.profilePicture);
        img.setImageBitmap(activity.profilePicture);
        TextView nameView = (TextView) activity.findViewById(R.id.textView);
        nameView.setText(activity.displayName);

        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        scaleImage(img, p);
        GridLayout grid = (GridLayout) getActivity().findViewById(R.id.ButtonContainer);
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

        Button activityFeed = (Button) new Button(this.getActivity());
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
        grid.addView(connections);
        grid.addView(activityFeed);




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
    private void scaleImage(ImageView view, Point point) throws NoSuchElementException {

       // ImageView imageView = (ImageView) this.getActivity().findViewById(R.id.imageView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = point.x;
        params.height = point.x;
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






