package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.NoSuchElementException;


public class EventCreation extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_creation, container, false);

        return v;
    }

    public static EventCreation newInstance(String text) {

        EventCreation f = new EventCreation();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){
        ImageButton imageButton = (ImageButton)getActivity().findViewById(R.id.generic_event);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              CreateEvent(v);
            }
        });
        super.onStart();
    }
    private void scaleImage(ImageView view, Point point) throws NoSuchElementException {

        // ImageView imageView = (ImageView) this.getActivity().findViewById(R.id.imageView);

        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.width = point.x/3;
        params.height = point.x *2 / 7;
        view.setLayoutParams(params);

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
    public void CreateEvent(View view){
        Intent intent = new Intent(this.getActivity(), OnEventCreation.class);
        startActivity(intent);
    }


}
