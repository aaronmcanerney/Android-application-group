package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.NoSuchElementException;


public class HomeProfile extends Fragment {
    public static int PLACE_PICKER_REQUEST = 1;
    /*  @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_profile
                , container, false);
        return v;
    }
    public static HomeProfile newInstance(String text) {

        HomeProfile f = new HomeProfile();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        MainActivity activity = (MainActivity) getActivity();

        RelativeLayout container = (RelativeLayout) this.getActivity().findViewById(R.id.home_profile_relative);

        container.setBackgroundColor(Color.parseColor("#d6dbe1"));
        RelativeLayout profileContent = new RelativeLayout(getActivity());
        container.addView(profileContent);
        profileContent.getLayoutParams().height = p.y/3;
        profileContent.getLayoutParams().width = p.x - 40;
        profileContent.setId(View.generateViewId());
        profileContent.setBackgroundResource(R.drawable.roundedlayout);
        ((RelativeLayout.LayoutParams) profileContent.getLayoutParams()).leftMargin = 20;
        ((RelativeLayout.LayoutParams) profileContent.getLayoutParams()).rightMargin = 20;

        FirebaseUser user = activity.getCurrentUser();
        Uri profilePictureUri = user.getPhotoUrl();
        ImageView profilePicture = new ImageView(getActivity());
        profilePicture.setId(View.generateViewId());
        profileContent.addView(profilePicture);
        scaleImage(profilePicture, p, p.y / 4);
        profilePicture.setId(View.generateViewId());
        Picasso.with(activity).load(profilePictureUri).transform(new CircleTransform()).into(profilePicture);
        RelativeLayout.LayoutParams picture = (RelativeLayout.LayoutParams) profilePicture.getLayoutParams();
        picture.addRule(RelativeLayout.CENTER_IN_PARENT);
        picture.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        profilePicture.setLayoutParams(picture);
        ((RelativeLayout.LayoutParams) profilePicture.getLayoutParams()).leftMargin = 20;


        TextView userInfo = new TextView(getActivity());
        profileContent.addView(userInfo);
        userInfo.setLines(2);
        userInfo.setText("Aaron Mcanerney \n Santa Cruz Calfornia");
        userInfo.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams infoParams = (RelativeLayout.LayoutParams) userInfo.getLayoutParams();
        infoParams.addRule(RelativeLayout.RIGHT_OF, profilePicture.getId());
        infoParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        infoParams.height = p.y / 10;
        infoParams.width = p.x /2 - 10;
        infoParams.leftMargin = 10;
        userInfo.setGravity(Gravity.CENTER);
        userInfo.setBackgroundResource(R.drawable.bluerounded);


        TextView tagContainer = new TextView(getActivity());
        container.addView(tagContainer);
        tagContainer.getLayoutParams().height = p.y / 5;
        tagContainer.getLayoutParams().width = p.x *2/3;
        RelativeLayout.LayoutParams tagParams = (RelativeLayout.LayoutParams) tagContainer.getLayoutParams();
        tagParams.addRule(RelativeLayout.BELOW, profileContent.getId());
        tagParams.topMargin = 50;
        tagParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tagParams.leftMargin = 20;
        tagContainer.setLayoutParams(tagParams);
        tagContainer.setBackgroundResource(R.drawable.bluerounded);
        tagContainer.setId(View.generateViewId());



        Button edit = new Button(getActivity());
        container.addView(edit);
        edit.setText("edit");
        edit.setTextColor(Color.parseColor("#3fa9f5"));
        RelativeLayout.LayoutParams editParams = (RelativeLayout.LayoutParams) edit.getLayoutParams();
        editParams.addRule(RelativeLayout.RIGHT_OF, tagContainer.getId());
        editParams.addRule(RelativeLayout.ALIGN_BOTTOM, tagContainer.getId());
        editParams.leftMargin = 20;
        edit.setBackgroundResource(R.drawable.roundedlayout);
        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toOnEventCreation(v);
            }
        });

        RelativeLayout friends = new RelativeLayout(getActivity());
        container.addView(friends);
        friends.setBackgroundResource(R.drawable.roundedlayout);
        friends.getLayoutParams().height = p.y /4;
        friends.getLayoutParams().width = p.x / 18 * 8;
        RelativeLayout.LayoutParams friendsParams = (RelativeLayout.LayoutParams) friends.getLayoutParams();
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        friendsParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        friendsParams.leftMargin = 20;
        friends.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toConnections(v);
            }
        });



        RelativeLayout addEvent = new RelativeLayout(getActivity());
        container.addView(addEvent);
        addEvent.setBackgroundResource(R.drawable.roundedlayout);
        addEvent.getLayoutParams().height = p.y /4;
        addEvent.getLayoutParams().width = p.x / 18 * 8;
        RelativeLayout.LayoutParams eventParams = (RelativeLayout.LayoutParams) addEvent.getLayoutParams();
        eventParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        eventParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        eventParams.rightMargin = 20;
        addEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               toOnEventCreation(v);

            }
        });


        super.onStart();
    }

    public void alert(String title, String message) {
        MainActivity activity = (MainActivity) getActivity();
        activity.alert(title, message);
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
    private void scaleImage(ImageView view, Point point, int i) throws NoSuchElementException {

       // ImageView imageView = (ImageView) this.getActivity().findViewById(R.id.imageView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = i;
        params.height = i;
        view.setLayoutParams(params);

    }
    private void scaleImage(ImageView view, Point point, int i, int y) throws NoSuchElementException {

        // ImageView imageView = (ImageView) this.getActivity().findViewById(R.id.imageView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = i;
        params.height = y;
        view.setLayoutParams(params);

    }
    public void toConnections(View view){
        Intent intent = new Intent(this.getActivity(), Connections.class) ;
        startActivity(intent);
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
/*
    public void test(View view){
        Fragment fragment = new SubmitEvent();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.photo_repository, fragment).addToBackStack(null).commit();
    }
    */



}






