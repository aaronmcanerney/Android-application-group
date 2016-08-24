package com.example.aaron.fragmenttest;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Event implements Comparable<Event> {

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

    public Event() {

    }

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

    public String getPlaceName() { return placeName; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }

    public void addConnection(String connectionId) {
        connections.add(connectionId);
    }
    public boolean hasConnection(String connectionId) { return connections.contains(connectionId); }
    public void removeConnection(String connectionId) { connections.remove(connectionId); }

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
        String time = Utilities.formatSystemDateAndTime(this);
        DatabaseReference requests = database.child("requests");
        DatabaseReference notifications = database.child("notifications");
        for (String connectionId : connections) {
            String response = (connectionId.equals(creatorId)) ? "accepted" : "pending";
            DatabaseReference request = requests.child(connectionId).child(eventId);
            request.child("status").setValue(response);
            request.child("time").setValue(time);

            DatabaseReference notification = notifications.child(connectionId).push();
            notification.child("text").setValue("This is a test notification!");
        }
    }

    @Override
    public int compareTo(@NonNull Event event) {
        if (year < event.getYear()) return -1;
        if (year > event.getYear()) return 1;

        if (month < event.getMonth()) return -1;
        if (month > event.getMonth()) return 1;

        if (day < event.getDay()) return -1;
        if (day > event.getDay()) return 1;

        if (hour < event.getHour()) return -1;
        if (hour > event.getHour()) return 1;

        if (minute < event.getMinute()) return -1;
        if (minute > event.getMinute()) return 1;

        return 0;
    }
}
