package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
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

                    toNextOne(v);
                }
            });
            RelativeLayout relativeLayout = (RelativeLayout) this.getActivity().findViewById(
                    R.id.event_container
            );
            relativeLayout.addView(button);

            super.onStart();
        }
    public void toNextOne(View view){
        Fragment fragment = new OnEventCreationTwo();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).commit();
    }

 }
