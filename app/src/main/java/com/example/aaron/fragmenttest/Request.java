package com.example.aaron.fragmenttest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rogowski on 9/12/2016.
 */
public class Request {

    public String status;
    public String time;

    public Request() {

    }

    public void push() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("requests");
        events.push().setValue(this);
    }

}
