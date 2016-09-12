package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


public class EventCreation extends Fragment {

    public static int PLACE_PICKER_REQUEST = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_creation, container, false);

        return v;
    }

    public static EventCreation newInstance(String text) {

        EventCreation f = new EventCreation();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){
        ImageButton imageButton = (ImageButton)getActivity().findViewById(R.id.generic_event);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toOnEventCreation(v);
            }
        });

        super.onStart();
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
    public void toOnEventCreation(View view){
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


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this.getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this.getActivity(), toastMsg, Toast.LENGTH_LONG).show();


                Intent intent = new Intent(this.getActivity(), UserCreateEvent.class);
                intent.putExtra("placeName", place.getName());
                intent.putExtra("placeAddress", place.getAddress());
                startActivity(intent);
            }
        }
    }


}
