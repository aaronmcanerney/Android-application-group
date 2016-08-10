package com.example.aaron.fragmenttest;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class UserPickDate extends Fragment {
    int hour_x;
    int minute_x;
    static final int DIALOG_ID = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_pick_date
                , container, false);
        return v;
    }
    public static UserPickDate newInstance(String text) {

        UserPickDate f = new UserPickDate();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){

        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);

        RelativeLayout rl = (RelativeLayout) this.getActivity().findViewById(R.id.event_creation_two);

        RelativeLayout setDateLayout = new RelativeLayout(getActivity());
        rl.addView(setDateLayout);
        RelativeLayout.LayoutParams dateLayoutParams = (RelativeLayout.LayoutParams) setDateLayout.getLayoutParams();
        dateLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        setDateLayout.setLayoutParams(dateLayoutParams);
        setDateLayout.getLayoutParams().height = point.y/3;
        setDateLayout.getLayoutParams().width = point.x /2;
        setDateLayout.setBackgroundColor(Color.parseColor("#e1e1e1"));
        ImageView img1 = new ImageView(getActivity());
        setDateLayout.addView(img1);
        RelativeLayout.LayoutParams img1Params = (RelativeLayout.LayoutParams) img1.getLayoutParams();
        img1Params.addRule(RelativeLayout.CENTER_IN_PARENT);
        img1.setBackgroundResource(R.drawable.roundedlayout);
        setDateLayout.setBackgroundResource(R.drawable.roundedlayout);
        Button date = new Button(getActivity());
        date.setClickable(false);
        date.setText("Select your date");
        setDateLayout.addView(date);
        date.setTextColor(Color.WHITE);
        date.setBackgroundResource(R.drawable.roundedbutton);
        RelativeLayout.LayoutParams dateParams = (RelativeLayout.LayoutParams) date.getLayoutParams();
        dateParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        date.setLayoutParams(dateParams);
        setDateLayout.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {

                onButtonClicked(v);
            }
        });

        RelativeLayout setTimeLayout = new RelativeLayout(getActivity());
        rl.addView(setTimeLayout);
        RelativeLayout.LayoutParams timeLayoutParams = (RelativeLayout.LayoutParams) setTimeLayout.getLayoutParams();
        timeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        setTimeLayout.setLayoutParams(timeLayoutParams);
        setTimeLayout.getLayoutParams().height = point.y/3;
        setTimeLayout.getLayoutParams().width = point.x/2;
        setTimeLayout.setBackgroundColor(Color.parseColor("#e1e1e1"));
        ImageView img2 = new ImageView(getActivity());
        setTimeLayout.addView(img2);
        RelativeLayout.LayoutParams img2Params = (RelativeLayout.LayoutParams) img2.getLayoutParams();
        img2Params.addRule(RelativeLayout.CENTER_IN_PARENT);
        img2.setBackgroundResource(R.drawable.roundedlayout);
        setTimeLayout.setBackgroundResource(R.drawable.roundedlayout);
        Button time = new Button(getActivity());
        time.setClickable(false);
        time.setText("Select your time");
        setTimeLayout.addView(time);
        time.setBackground(getActivity().getResources().getDrawable(R.drawable.roundedbutton));
        time.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams timeParams = (RelativeLayout.LayoutParams) time.getLayoutParams();
        timeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        time.setLayoutParams(timeParams);
        setTimeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Toast.makeText(getActivity(), selectedHour + ":" + selectedMinute, Toast.LENGTH_LONG).show();
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });





        Button next = new Button(getActivity());
        next.setText("Next");
        rl.addView(next);
        next.setTextColor(Color.WHITE);
        next.setBackground(getActivity().getResources().getDrawable(R.drawable.roundedbutton));
        RelativeLayout.LayoutParams nextParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        next.setLayoutParams(nextParams);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                toEventCreationThree(v);
            }
        });



        super.onStart();
    }

    public void onButtonClicked(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(),"Date Picker");
    }



    public void toEventCreationThree(View view){
        Fragment fragment = new UserPickTime();
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
