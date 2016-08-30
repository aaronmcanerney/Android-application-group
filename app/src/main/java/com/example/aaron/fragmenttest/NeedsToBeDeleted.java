package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//i added a comment
public class NeedsToBeDeleted extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(3F, .8F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_not_use);
        Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        //hello
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.fragment_layout);
        for(int i = 0; i < 100; i++){
            RelativeLayout rl = new RelativeLayout(this);
            ImageView img = new ImageView(this);
            img.setImageResource(R.mipmap.calendar);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            rl.addView(img);
            TextView tv = new TextView(this);
            String temp = "Title: " + i;
            //
            SpannableString spanString = new SpannableString(temp);
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            tv.setText(spanString);
            TextView loc = new TextView(this);
            TextView time = new TextView(this);
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
            //random commments

            rl.addView(time);
            RelativeLayout.LayoutParams p2 = (RelativeLayout.LayoutParams) time.getLayoutParams();
            loc.setId(View.generateViewId());
            p2.leftMargin = point.x /2;
            p2.addRule(RelativeLayout.BELOW, loc.getId());




            // rl.setClickable(true);
            rl.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    v.startAnimation(buttonClick);
                    Toast.makeText(getApplicationContext(),"Clicked layout " , Toast.LENGTH_SHORT).show();
                }
            });

        }

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
