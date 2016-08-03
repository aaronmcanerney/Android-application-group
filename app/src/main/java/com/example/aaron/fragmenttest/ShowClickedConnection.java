package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ShowClickedConnection extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_clicked_connection
                , container, false);
        return v;
    }
    public static ShowClickedConnection newInstance(String text) {

        ShowClickedConnection f = new ShowClickedConnection();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){
        Display d = ((WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);


        ImageView imageView = new ImageView(getActivity());
        RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        imgParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        imgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imageView.setId(View.generateViewId());


        TextView name = new TextView(getActivity());
        RelativeLayout.LayoutParams nameParams = (RelativeLayout.LayoutParams) name.getLayoutParams();
        nameParams.addRule(RelativeLayout.LEFT_OF, imageView.getId());
        name.setId(View.generateViewId());


        TextView bio = new TextView(getActivity());
        RelativeLayout.LayoutParams bioParams = (RelativeLayout.LayoutParams) bio.getLayoutParams();
        bioParams.addRule(RelativeLayout.BELOW,name.getId());
        bio.setId(View.generateViewId());


        ScrollView scrollView = new ScrollView(getActivity());
        RelativeLayout.LayoutParams scrollViewParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        scrollViewParams.addRule(RelativeLayout.BELOW, bio.getId());







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
