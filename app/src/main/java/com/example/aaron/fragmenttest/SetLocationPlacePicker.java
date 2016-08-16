package com.example.aaron.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SetLocationPlacePicker extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_set_event_info
                , container, false);
        return v;
    }

    public static SetLocationPlacePicker newInstance(String text) {

        SetLocationPlacePicker f = new SetLocationPlacePicker();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onStart() {

        super.onStart();
    }



    public void toEventCreationTwo(View view) {
        Fragment fragment = new UserPickDateAndTime();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
    }

}