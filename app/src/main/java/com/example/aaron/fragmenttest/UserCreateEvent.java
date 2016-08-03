package com.example.aaron.fragmenttest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserCreateEvent extends AppCompatActivity {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_event_creation);
        Fragment fragment = new UserSetEventInfo();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();

        // Create event and add place
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Fatal error: no user", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        String placeName = intent.getStringExtra("placeName");
        String placeAddress = intent.getStringExtra("placeAddress");

        event = new Event(user.getUid());
        event.setPlaceName(placeName);
        event.setPlaceAddress(placeAddress);
        event.push();
    }


}
