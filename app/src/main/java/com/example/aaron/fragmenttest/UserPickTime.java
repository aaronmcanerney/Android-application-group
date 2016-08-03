package com.example.aaron.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;


public class UserPickTime extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_pick_time
                , container, false);
        return v;
    }
    public static UserPickTime newInstance(String text) {

        UserPickTime f = new UserPickTime();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void onStart(){
        TimePicker timePicker = (TimePicker) getActivity().findViewById(R.id.userTimePicker);
        Button button = (Button) getActivity().findViewById(R.id.nextFragment);
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "You clicked the buttton", Toast.LENGTH_LONG).show();
            }
        });
        super.onStart();
    }

}
