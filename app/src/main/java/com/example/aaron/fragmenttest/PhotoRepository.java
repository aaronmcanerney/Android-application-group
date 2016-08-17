package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;


public class PhotoRepository extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_repository
                , container, false);
        return v;
    }

    public static PhotoRepository newInstance(String text) {

        PhotoRepository f = new PhotoRepository();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);

        LinearLayout container = (LinearLayout) getActivity().findViewById(R.id.photo_repository);
        ScrollView scrollView = new ScrollView(getActivity());
        container.addView(scrollView);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));
        for(int i  = 0; i < 10; i++){
            createLayout(scrollView, p);
        }
        super.onStart();
    }

    public void createLayout(ScrollView container, Point point){
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        container.addView(relativeLayout);

        LinearLayout.LayoutParams relativeParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();

        relativeLayout.getLayoutParams().height = point.y * 18 / 100;
        relativeLayout.getLayoutParams().width = point.x * 15 / 16;
        relativeParams.topMargin = point.y / 50;
        relativeParams.leftMargin = point.x /32;
        relativeLayout.setBackgroundResource(R.drawable.roundedlayout);

        ImageButton button = new ImageButton(getActivity());
        button.setBackgroundResource(R.mipmap.ic_launcher);
        relativeLayout.addView(button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toPhotoGrid(v);

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

    public void toPhotoGrid(View v){
        Fragment fragment = new UserSubmitEvent();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id., fragment).addToBackStack(null).commit();
    }

}