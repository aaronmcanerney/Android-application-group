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
    private int numEntriesLoaded;
    private int numEntriesToLoad;

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
        // Set BGColor of fragment

        //LinearLayout linearLayout = (LinearLayout) this.getActivity().findViewById(R.id.fragment_layout);
        //linearLayout.setBackgroundColor(Color.parseColor("#d6dbe1"));

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

    public void loadEvent(String eventId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("events");
        events.child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eventId = snapshot.getKey();
                Map<String, Object> map = new HashMap<>();

                // Get all data from firebase
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    Object value = child.getValue(Object.class);
                    map.put(key, value);
                }

                if (map.isEmpty()) return;

                Event temp = new Event();

                // Populate name
                String name = (String) map.get("name");
                SpannableString nameFormatted = new SpannableString(name);
                nameFormatted.setSpan(new UnderlineSpan(), 0, nameFormatted.length(), 0);
                nameFormatted.setSpan(new StyleSpan(Typeface.BOLD), 0, nameFormatted.length(), 0);
                nameFormatted.setSpan(new StyleSpan(Typeface.ITALIC), 0, nameFormatted.length(), 0);

                temp.setName(nameFormatted.toString());
                temp.setDescription((String) map.get("description"));
                temp.setPlaceName((String) map.get("placeName"));
                Long year = (Long) map.get("year");
                Long month = (Long) map.get("month");
                Long day = (Long) map.get("day");
                String date = Utilities.formatDate(year, month, day);
                temp.setDate(date);
                Long hour = (Long) map.get("hour");
                Long minute = (Long) map.get("minute");
                String time = Utilities.formatTime(hour, minute);
                temp.setTime(time);

                eventContainer.add(temp);
                numEntriesLoaded++;

                if (numEntriesLoaded == numEntriesToLoad) {
                    swipe.setRefreshing(false);
                    Event[] tempArray = eventContainer.toArray(new Event[eventContainer.size()]);
                    List<Event> eventList = Arrays.asList(tempArray);
                    hold.setAdapter(new CalendarAdapter(getActivity(), eventList));
                }

                /*
                TextView textView = (TextView) event.findViewWithTag("name");
                textView.setText(nameFormatted);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.bluerounded);

                // Populate description
                String desc = (String) map.get("description");
                textView = (TextView) event.findViewWithTag("description");
                textView.setText(desc);

                // Populate place name
                String placeName = (String) map.get("placeName");
                textView = (TextView) event.findViewWithTag("placeName");
                textView.setText(placeName);

                // Populate date
                Long year = (Long) map.get("year");
                Long month = (Long) map.get("month");
                Long day = (Long) map.get("day");
                String date = Utilities.formatDate(year, month, day);
                textView = (TextView) event.findViewWithTag("date");
                textView.setText(date);

                // Populate time
                Long hour = (Long) map.get("hour");
                Long minute = (Long) map.get("minute");
                String time = Utilities.formatTime(hour, minute);
                textView = (TextView) event.findViewWithTag("time");
                textView.setText(time);

                TextView status = (TextView) event.findViewWithTag("status");
                String eventStatus = (String) status.getText();

                // What should we do with this status? Change background color?
                // If so, we can do that in 'buildCalendarEvent'
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    private void loadEvents(){
        numEntriesLoaded = 0;
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
                numEntriesToLoad = (int) snapshot.getChildrenCount();
                if (numEntriesToLoad == 0) swipe.setRefreshing(false);
                for (DataSnapshot request : snapshot.getChildren()) {
                    String eventId = request.getKey();
                    String status = request.child("status").getValue(String.class);
                    loadEvent(eventId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
