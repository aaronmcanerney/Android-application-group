package com.example.aaron.fragmenttest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class UserPickDateAndTime extends Fragment {
    TextView display;
    String date;
    String time;
    String at;
    String displayedDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_pick_date
                , container, false);
        return v;
    }
    public static UserPickDateAndTime newInstance(String text) {

        UserPickDateAndTime f = new UserPickDateAndTime();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){
        Calendar initialTime = Calendar.getInstance();
        int day = initialTime.get(Calendar.DAY_OF_MONTH);
        int month = initialTime.get(Calendar.MONTH);
        int year = initialTime.get(Calendar.YEAR);
        int hour = initialTime.get(Calendar.HOUR_OF_DAY);
        int minute = initialTime.get(Calendar.MINUTE);

        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);

        RelativeLayout container = (RelativeLayout) this.getActivity().findViewById(R.id.event_creation_two);

        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

        RelativeLayout timeDateButtons = new RelativeLayout(getActivity());
        container.addView(timeDateButtons);
        timeDateButtons.setBackgroundResource(R.drawable.roundedlayout);
        RelativeLayout.LayoutParams timeDateButtonsParams = (RelativeLayout.LayoutParams) timeDateButtons.getLayoutParams();
        timeDateButtonsParams.height = p.y * 5 /16;
        timeDateButtonsParams.width = p.x * 15/16;
        timeDateButtonsParams.leftMargin = p.x/32;
        timeDateButtonsParams.topMargin = p.y /32;
        timeDateButtons.setId(View.generateViewId());

        Button dateSelect = new Button(getActivity());
        timeDateButtons.addView(dateSelect);
        dateSelect.setBackgroundResource(R.drawable.bluerounded);
        dateSelect.setTextColor(Color.WHITE);
        dateSelect.setText("Select a Date");
        RelativeLayout.LayoutParams dateSelectParams = (RelativeLayout.LayoutParams) dateSelect.getLayoutParams();
        dateSelectParams.height = p.y / 5;
        dateSelectParams.width = p.x * 6 / 16 ;
        dateSelectParams.leftMargin = p.x / 32;
        dateSelectParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        dateSelectParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        dateSelect.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Toast.makeText(getActivity(), month + "/" + day + "/" + year, Toast.LENGTH_LONG).show();
                        display.setText(Utilities.formatDate(year, month, day) + at + time);

                        // Set event data (firebase)
                        UserCreateEvent activity = (UserCreateEvent) getActivity();
                        activity.event.setMonth(month);
                        activity.event.setDay(day);
                        activity.event.setYear(year);
                    }
                }, year, month, day);
                datePicker.setTitle("Select Time");
                datePicker.show();
            }
        });


        Button timeSelect = new Button(getActivity());
        timeDateButtons.addView(timeSelect);
        timeSelect.setBackgroundResource(R.drawable.bluerounded);
        timeSelect.setTextColor(Color.WHITE);
        timeSelect.setText("Select a Time");
        RelativeLayout.LayoutParams timeSelectParams = (RelativeLayout.LayoutParams) timeSelect.getLayoutParams();
        timeSelectParams.height = p.y / 5;
        timeSelectParams.width = p.x * 6 / 16 ;
        timeSelectParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        timeSelectParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        timeSelectParams.rightMargin = p.x / 32;

        timeSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Toast.makeText(getActivity(), selectedHour + ":" + selectedMinute, Toast.LENGTH_LONG).show();
                        display.setText( date + at + Utilities.formatTime(selectedHour, selectedMinute));

                        // Set event data (firebase)
                        UserCreateEvent activity = (UserCreateEvent) getActivity();
                        activity.event.setHour(selectedHour);
                        activity.event.setMinute(selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        RelativeLayout background = new RelativeLayout(getActivity());
        container.addView(background);
        background.setBackgroundResource(R.drawable.roundedlayout);
        RelativeLayout.LayoutParams backgroundParams = (RelativeLayout.LayoutParams) background.getLayoutParams();
        backgroundParams.height = p.y *6/16;
        backgroundParams.width = p.x * 15/16;
        backgroundParams.topMargin = p.y / 32;
        backgroundParams.addRule(RelativeLayout.BELOW, timeDateButtons.getId());
        backgroundParams.leftMargin = p.x / 32;

        date = Utilities.formatDate(year, month, day);
         at = " @ ";
         time = Utilities.formatTime(hour, minute);



        display = new TextView(getActivity());
        background.addView(display);
        display.setBackgroundResource(R.drawable.bluerounded);
        display.setTextColor(Color.WHITE);
        display.setGravity(Gravity.CENTER);
        display.setText(date + at + time);
        RelativeLayout.LayoutParams displayParams = (RelativeLayout.LayoutParams) display.getLayoutParams();
        displayParams.width = p.x * 5 /8;
        displayParams.height = p.y /5;
        //displayParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        displayParams.addRule(RelativeLayout.CENTER_IN_PARENT);
       // displayParams.bottomMargin = p.y / 10;


        Button next = new Button(getActivity());
        container.addView(next);
        next.setTextColor(Color.WHITE);
        next.setText("Next");
        next.setGravity(Gravity.CENTER);
        next.setBackgroundResource(R.drawable.bluerounded);
        RelativeLayout.LayoutParams nextParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        nextParams.height = p.y/15;
        nextParams.width = p.x/4;
        nextParams.rightMargin = p.x/32;
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toSubmitEvent(v);
            }
        });

        super.onStart();
    }

    public void pickDate(View v){
        //DialogFragment newFragment = new DatePickerFragment();
        //newFragment.show(getActivity().getFragmentManager(),"Date Picker");
    }

    public void pickTime(View v) {
        //DialogFragment newFragment = new TimePickerFragment();
        //newFragment.
        //newFragment.show(getActivity().getFragmentManager(),"Time Picker");
    }

    public void toSubmitEvent(View view){
        Fragment fragment = new SubmitEvent();
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
