package com.example.aaron.fragmenttest;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Event implements Comparable<Event> {

    public String creatorId;
    public String placeName;
    public String placeAddress;
    public String name;
    public String description;
    public int hour;
    public int minute;
    public int year;
    public int month;
    public int day;
    private String date;
    private String time;
    private ArrayList<String> connections; // ArrayList of uid's

    public Event() {
        connections = new ArrayList<>();
    }

    public Event(String creatorId) {
        this();
        this.creatorId = creatorId;
        this.connections = new ArrayList<>();

        // Add creator as connection
        addConnection(creatorId);
    }


    public void setDate(String date){this.date = date;}
    public void setTime(String time){this.time = time;}

    public String getDate(){return date;}
    public String getTime(){return time;}

    public void addConnection(String connectionId) {
        connections.add(connectionId);
    }
    public boolean hasConnection(String connectionId) { return connections.contains(connectionId); }
    public void removeConnection(String connectionId) { connections.remove(connectionId); }

    public void push() {
        // Create event
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("events");
        DatabaseReference event = events.push();
        String eventId = event.getKey();
        event.setValue(this);

        // Set shared notification info
        DatabaseReference sharedNotifications = database.child("shared-notifications");
        DatabaseReference notificationRef = sharedNotifications.push();
        String notificationId = notificationRef.getKey();
        SharedNotification notification = new SharedNotification();
        notification.profilePictureURI = "";
        notification.text = "Roman Rogowski has invited you to an event!";
        notificationRef.setValue(notification);

        // Push requests/notifications for event
        String time = Utilities.formatSystemDateAndTime(this);
        DatabaseReference requests = database.child("requests");
        DatabaseReference notifications = database.child("notifications");
        for (String connectionId : connections) {
            String status = (connectionId.equals(creatorId)) ? "accepted" : "pending";
            DatabaseReference request = requests.child(connectionId).child(eventId);
            Request r = new Request();
            r.status = status;
            r.time = time;
            request.setValue(r);

            notifications.child(connectionId).child(notificationId).setValue(true);
        }
    }

    @Override
    public int compareTo(@NonNull Event event) {
        if (year < event.year) return -1;
        if (year > event.year) return 1;

        if (month < event.month) return -1;
        if (month > event.month) return 1;

        if (day < event.day) return -1;
        if (day > event.day) return 1;

        if (hour < event.hour) return -1;
        if (hour > event.hour) return 1;

        if (minute < event.minute) return -1;
        if (minute > event.minute) return 1;

        return 0;
    }
}
