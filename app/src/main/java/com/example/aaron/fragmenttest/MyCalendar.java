package com.example.aaron.fragmenttest;


import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MyCalendar extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(3F, .8F);
    public MyCalendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestCalendar.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCalendar newInstance(String param1, String param2) {
        MyCalendar fragment = new MyCalendar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_calendar, container, false);
    }
    @Override
    public void onStart(){
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        LinearLayout linearLayout = (LinearLayout) this.getActivity().findViewById(R.id.fragment_layout);
        for(int i = 0; i < 100; i++){
            RelativeLayout rl = new RelativeLayout(this.getActivity());
            ImageView img = new ImageView(this.getActivity());
            img.setImageResource(R.mipmap.calendar);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            rl.addView(img);
            TextView tv = new TextView(this.getActivity());
            String temp = "Title: " + i;
            //
            SpannableString spanString = new SpannableString(temp);
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            tv.setText(spanString);
            TextView loc = new TextView(this.getActivity());
            TextView time = new TextView(this.getActivity());
            loc.setText("Location: " + i);
            time.setText("Time: " + i);
            // Need to make text size dynamic based on screen size
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            linearLayout.addView(rl);
            rl.addView(tv);
            rl.getLayoutParams().height = point.y/4;
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            p.leftMargin = point.x /2;
            tv.setLayoutParams(p);
            rl.addView(loc);
            RelativeLayout.LayoutParams p1 = (RelativeLayout.LayoutParams) loc.getLayoutParams();
            p1.leftMargin = point.x /2;

            tv.setId(View.generateViewId());
            p1.addRule(RelativeLayout.BELOW, tv.getId());
            loc.setLayoutParams(p1);

            rl.addView(time);
            RelativeLayout.LayoutParams p2 = (RelativeLayout.LayoutParams) time.getLayoutParams();
            loc.setId(View.generateViewId());
            p2.leftMargin = point.x /2;
            p2.addRule(RelativeLayout.BELOW, loc.getId());




            // rl.setClickable(true);
            rl.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    v.startAnimation(buttonClick);
                    Toast.makeText(getActivity(), "Clicked layout " , Toast.LENGTH_SHORT).show();
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
