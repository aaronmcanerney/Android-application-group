package com.example.aaron.fragmenttest;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyCalendar extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(3F, .8F);
    private ListView hold;
    private SwipeRefreshLayout swipe;
    private ArrayList<Event> eventContainer;
    private FirebaseWaitLoader loader;

    public static MyCalendar newInstance(String param1, String param2) {
        MyCalendar fragment = new MyCalendar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_calendar, container, false);
    }
    @Override
    public void onStart(){
        // Load events
        hold = (ListView) getActivity().findViewById(R.id.calender_list);
        hold.setBackgroundColor(Color.parseColor("#d6dbe1"));
        loadEvents();

        swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.calendar_swipe_refresh);
        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Toast.makeText(getActivity(), "refreshing", Toast.LENGTH_LONG).show();
                        loadEvents();
                    }
                }
        );

        super.onStart();
    }

    private void loadEvents(){
        eventContainer = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        String uid = user.getUid();
        DatabaseReference requests = database.child("requests").child(uid);
        String now = Utilities.formatSystemDateAndTimeAtCurrentMoment();
        requests.orderByChild("time").startAt(now).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int numToLoad = (int) snapshot.getChildrenCount();
                loader = new FirebaseWaitLoader(numToLoad);
                if (loader.done()) swipe.setRefreshing(false);
                for (DataSnapshot request : snapshot.getChildren()) {
                    Request r = request.getValue(Request.class);
                    String eventId = request.getKey();
                    String status = r.status;
                    loadEvent(eventId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadEvent(String eventId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("events");
        events.child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eventId = snapshot.getKey();
                Event event = snapshot.getValue(Event.class);

                // Populate name and other data
                SpannableString nameFormatted = new SpannableString(event.name);
                nameFormatted.setSpan(new UnderlineSpan(), 0, nameFormatted.length(), 0);
                nameFormatted.setSpan(new StyleSpan(Typeface.BOLD), 0, nameFormatted.length(), 0);
                nameFormatted.setSpan(new StyleSpan(Typeface.ITALIC), 0, nameFormatted.length(), 0);
                event.name = nameFormatted.toString();

                int year = event.year;
                int month = event.month;
                int day = event.day;
                String date = Utilities.formatDate(year, month, day);
                event.setDate(date);
                int hour = event.hour;
                int minute = event.minute;
                String time = Utilities.formatTime(hour, minute);
                event.setTime(time);

                eventContainer.add(event);
                loader.update();

                if (loader.done()) {
                    swipe.setRefreshing(false);
                    Event[] tempArray = eventContainer.toArray(new Event[eventContainer.size()]);
                    List<Event> eventList = Arrays.asList(tempArray);
                    hold.setAdapter(new CalendarAdapter(getActivity(), eventList));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loader.update();
            }
        });
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

    public void OnCalendarClicked(View view){
        Intent intent = new Intent(this.getActivity(), OnCalendarClicked.class);
        startActivity(intent);
    }




}
