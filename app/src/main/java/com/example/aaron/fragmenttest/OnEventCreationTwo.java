package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class OnEventCreationTwo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_on_event_creation_two
                , container, false);
        return v;
    }
    public static OnEventCreationTwo newInstance(String text) {

        OnEventCreationTwo f = new OnEventCreationTwo();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        Button button = new Button(this.getActivity());
        final Activity activity = this.getActivity();
        button.setText("Next");
        RelativeLayout rl = (RelativeLayout) this.getActivity().findViewById(R.id.event_creation_two);
        DatePicker datePicker = new DatePicker(this.getActivity());
        rl.addView(datePicker);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar c = Calendar.getInstance();
        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        final String date = (c.getTime() +"").substring(0,10);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(activity, date, Toast.LENGTH_LONG).show();
                toEventCreationThree(v);
            }
        });

        //datePicker

        RelativeLayout.LayoutParams dateParams = (RelativeLayout.LayoutParams) datePicker.getLayoutParams();
        dateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        datePicker.getLayoutParams().height = point.y *3/4;
        datePicker.getLayoutParams().width = point.x;
        //dateParams.topMargin = point.y/10;
        datePicker.setLayoutParams(dateParams);
        rl.addView(button);
        RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);




        super.onStart();
    }
    public void toEventCreationThree(View view){
        Fragment fragment = new OnEventCreationThree();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
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
