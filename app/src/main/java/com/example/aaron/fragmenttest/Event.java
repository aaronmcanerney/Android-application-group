package com.example.aaron.fragmenttest;

import android.location.Location;

import java.util.ArrayList;

public class Event {

    private String creatorId;
    private String name;
    private String description;
    private Location location;
    private ArrayList<String> invitees; // ArrayList of uid's

    public Event(String creatorId, String name, String description) {
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
    }

    public void setLocation(Location location) {
        
    }

}