package com.example.aaron.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


public class OnEventCreationOne extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_on_event_creation_one
                , container, false);
        return v;
    }
    public static OnEventCreationOne newInstance(String text) {

        OnEventCreationOne f = new OnEventCreationOne();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){

        Button button = new Button(this.getActivity());
        button.setText("Next");
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                toEventCreationTwo(v);
            }
        });
        RelativeLayout rl = (RelativeLayout) this.getActivity().findViewById(R.id.event_creation_one);
        rl.addView(button);

        super.onStart();
    }
    public void toEventCreationTwo(View view){
        Fragment fragment = new OnEventCreationTwo();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
    }

}