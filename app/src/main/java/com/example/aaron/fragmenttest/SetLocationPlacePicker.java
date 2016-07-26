package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


public class SetLocationPlacePicker extends Fragment {
    public static int PLACE_PICKER_REQUEST = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_on_event_creation_one
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

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this.getActivity()), PLACE_PICKER_REQUEST);
        }
        catch(GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }
        catch(GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
        }
        super.onStart();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this.getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this.getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
            Fragment fragment = new OnEventCreationOne();
            FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
        }
    }


    public void toEventCreationTwo(View view) {
        Fragment fragment = new OnEventCreationTwo();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
    }

}