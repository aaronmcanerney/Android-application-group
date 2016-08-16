package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by aaron_000 on 8/15/2016.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ImageView img  = (ImageView) findViewById(R.id.splash);
        Display d = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        img.getLayoutParams().height = p.y;
        img.getLayoutParams().width = p.x;

        Thread thread = new Thread(){
            @Override
            public void run() {
                //here is where you tell it how long to wait
                try {
                    //modify the firebase loading here i think
                    sleep(3000);
                    Intent intent  =  new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
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
