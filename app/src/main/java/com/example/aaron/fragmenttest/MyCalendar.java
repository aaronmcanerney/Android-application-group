package com.example.aaron.fragmenttest;


import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MyCalendar extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(3F, .8F);
    private Map<String, RelativeLayout> myEvents;

    public MyCalendar() {
        // Required empty public constructor
    }

    public static MyCalendar newInstance(String param1, String param2) {
        MyCalendar fragment = new MyCalendar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_calendar, container, false);
    }
    @Override
    public void onStart(){
        // Load events
        myEvents = new HashMap<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        String uid = user.getUid();
        DatabaseReference requests = database.child("requests");
        requests.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String eventId = child.getKey();
                    String status = child.getValue(String.class);
                    RelativeLayout event = buildCalendarEvent(status);
                    myEvents.put(eventId, event);
                    loadEvent(eventId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        super.onStart();
    }

    public void loadEvent(String eventId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("events");
        events.child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eventId = snapshot.getKey();
                RelativeLayout event = myEvents.get(eventId);
                Map<String, Object> map = new HashMap<>();

                // Get all data from firebase
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    Object value = child.getValue(Object.class);
                    map.put(key, value);
                }

                if (map.isEmpty()) return;

                // Populate name
                String name = (String) map.get("name");
                SpannableString nameFormatted = new SpannableString(name);
                nameFormatted.setSpan(new UnderlineSpan(), 0, nameFormatted.length(), 0);
                nameFormatted.setSpan(new StyleSpan(Typeface.BOLD), 0, nameFormatted.length(), 0);
                nameFormatted.setSpan(new StyleSpan(Typeface.ITALIC), 0, nameFormatted.length(), 0);
                TextView textView = (TextView) event.findViewWithTag("name");
                textView.setText(nameFormatted);

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private RelativeLayout buildCalendarEvent(String eventStatus) {
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        LinearLayout linearLayout = (LinearLayout) this.getActivity().findViewById(R.id.fragment_layout);
        RelativeLayout rl = new RelativeLayout(this.getActivity());
        linearLayout.addView(rl, 0);
        rl.getLayoutParams().height = point.y/4;

        // Build Calendar Image
        ImageView img = new ImageView(this.getActivity());
        img.setImageResource(R.mipmap.calendar);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        rl.addView(img);
        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Toast.makeText(getActivity(), "Clicked layout " , Toast.LENGTH_SHORT).show();
            }
        });

        // Add status (pending, accepted, rejected), but hide; will be useful later
        TextView status = new TextView(this.getActivity());
        status.setTag("status");
        status.setText(eventStatus);
        status.setVisibility(View.GONE);
        rl.addView(status);

        TextView name = new TextView(this.getActivity());
        name.setTag("name");
        name.setId(View.generateViewId());
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        rl.addView(name);
        RelativeLayout.LayoutParams pname = (RelativeLayout.LayoutParams) name.getLayoutParams();
        pname.leftMargin = point.x /2;
        name.setLayoutParams(pname);

        TextView desc = new TextView(this.getActivity());
        desc.setTag("description");
        desc.setId(View.generateViewId());
        rl.addView(desc);
        RelativeLayout.LayoutParams pdesc = (RelativeLayout.LayoutParams) desc.getLayoutParams();
        pdesc.leftMargin = point.x / 2;
        pdesc.addRule(RelativeLayout.BELOW, name.getId());
        desc.setLayoutParams(pdesc);

        TextView loc = new TextView(this.getActivity());
        loc.setTag("placeName");
        loc.setId(View.generateViewId());
        rl.addView(loc);
        RelativeLayout.LayoutParams ploc = (RelativeLayout.LayoutParams) loc.getLayoutParams();
        ploc.leftMargin = point.x / 2;
        ploc.addRule(RelativeLayout.BELOW, desc.getId());
        loc.setLayoutParams(ploc);

        TextView date = new TextView(this.getActivity());
        date.setTag("date");
        date.setId(View.generateViewId());
        rl.addView(date);
        RelativeLayout.LayoutParams pdate = (RelativeLayout.LayoutParams) date.getLayoutParams();
        pdate.leftMargin = point.x / 2;
        pdate.addRule(RelativeLayout.BELOW, loc.getId());
        date.setLayoutParams(pdate);

        TextView time = new TextView(this.getActivity());
        time.setTag("time");
        time.setId(View.generateViewId());
        rl.addView(time);
        RelativeLayout.LayoutParams ptime = (RelativeLayout.LayoutParams) time.getLayoutParams();
        ptime.leftMargin = point.x / 2;
        ptime.addRule(RelativeLayout.BELOW, date.getId());
        time.setLayoutParams(ptime);

        return rl;
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


}
