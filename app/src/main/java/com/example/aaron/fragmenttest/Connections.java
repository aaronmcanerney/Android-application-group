package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.NoSuchElementException;

public class Connections extends AppCompatActivity {
    public static final int colNum = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        GridLayout gridLayout = (GridLayout) this.findViewById(R.id.connections_container);
        gridLayout.setColumnCount(colNum);
        for (int i = 0; i < 20; i++) {
            ImageButton button = new ImageButton(this);
            button.setImageResource(R.mipmap.connect);
            button.setScaleType(ImageView.ScaleType.CENTER);
            button.setClickable(true);
            gridLayout.addView(button);
            scaleImage(button);


        }
    }

    private void scaleImage(ImageView view) throws NoSuchElementException {
        Display d = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(10, 10, 5, 5);
        params.width = p.x/3 - 15;
        params.height = p.y * 2 / 7 ;
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

}
