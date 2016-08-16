package com.example.aaron.fragmenttest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Event {

    private String creatorId;
    private String placeName;
    private String placeAddress;
    private String name;
    private String description;
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;
    private ArrayList<String> connections; // ArrayList of uid's

    public Event(String creatorId) {
        this.creatorId = creatorId;
        this.connections = new ArrayList<>();

        // Add creator as connection
        addConnection(creatorId);
    }

    public void setPlaceName(String placeName) { this.placeName = placeName; }
    public void setPlaceAddress(String placeAddress) { this.placeAddress = placeAddress; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setHour(int hour) { this.hour = hour; }
    public void setMinute(int minute) { this.minute = minute; }
    public void setYear(int year) { this.year = year; }
    public void setMonth(int month) { this.month = month; }
    public void setDay(int day) { this.day = day; }

    public void addConnection(String connectionId) {
        connections.add(connectionId);
    }

    public void push() {
        // Create event
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("events");
        String eventId = events.push().getKey();
        DatabaseReference event = events.child(eventId);
        event.child("creatorId").setValue(creatorId);
        event.child("placeName").setValue(placeName);
        event.child("placeAddress").setValue(placeAddress);
        event.child("name").setValue(name);
        event.child("description").setValue(description);
        event.child("hour").setValue(hour);
        event.child("minute").setValue(minute);
        event.child("year").setValue(year);
        event.child("month").setValue(month);
        event.child("day").setValue(day);

        // Push requests for event
        DatabaseReference requests = database.child("requests");
        for (String connectionId : connections) {
            String response = (connectionId.equals(creatorId)) ? "accepted" : "pending";
            requests.child(connectionId).child(eventId).setValue(response);
        }
    }

}
