package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserSetEventInfo extends Fragment {

    EditText editDescription;
    EditText editName;
    RelativeLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_set_event_info
                , container, false);
        return v;
    }
    public static UserSetEventInfo newInstance(String text) {

        UserSetEventInfo f = new UserSetEventInfo();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){


        //format the name again
        SpannableString nameFormatted = new SpannableString("Event Name: ");
        nameFormatted.setSpan(new UnderlineSpan(), 0, nameFormatted.length(), 0);
        nameFormatted.setSpan(new StyleSpan(Typeface.BOLD), 0, nameFormatted.length(), 0);
        nameFormatted.setSpan(new StyleSpan(Typeface.ITALIC), 0, nameFormatted.length(), 0);
        //get the linear layout so everything is aligned vertical


        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);

        container = (RelativeLayout) getActivity().findViewById(R.id.user_set_info_relative);
        container.setBackgroundColor(Color.parseColor("#d6dbe1"));

        TextView eventName = new TextView(getActivity());
        container.addView(eventName);
        eventName.setText("Event Name");
        eventName.setBackgroundResource(R.drawable.bluerounded);
        eventName.setTextColor(Color.WHITE);
        eventName.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams eventNameParams = (RelativeLayout.LayoutParams) eventName.getLayoutParams();
        eventNameParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        eventNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        eventNameParams.leftMargin = p.x/32;
        eventNameParams.width = p.x /2;
        eventNameParams.height = p.y / 16;
        eventNameParams.topMargin = p.y/32;
       // need top margin
        eventName.setId(View.generateViewId());

        editName = new EditText(getActivity());
        container.addView(editName);
        editName.setGravity(Gravity.LEFT);
        editName.setGravity(Gravity.TOP);
        RelativeLayout.LayoutParams editNameParams = (RelativeLayout.LayoutParams) editName.getLayoutParams();
        editNameParams.height = p.y/6;
        editNameParams.width = p.x * 15/16;
        editNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        editNameParams.addRule(RelativeLayout.BELOW, eventName.getId());
       // editNameParams.topMargin = p.y/ constant;
        editNameParams.leftMargin = p.x/32;
        editName.setBackgroundResource(R.drawable.roundedlayout);
        editName.setId(View.generateViewId());
        editNameParams.topMargin = p.y/32;
        editName.setTag("name");
        editName.setHint("Event Name");





        TextView description = new TextView(getActivity());
        container.addView(description);
        description.setText("Description");
        description.setBackgroundResource(R.drawable.bluerounded);
        description.setTextColor(Color.WHITE);
        description.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams descriptionParams = (RelativeLayout.LayoutParams) description.getLayoutParams();
        descriptionParams.addRule(RelativeLayout.BELOW, editName.getId());
        descriptionParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        descriptionParams.leftMargin = p.x/32;
        descriptionParams.width = p.x /2;
        descriptionParams.height = p.y / 16;
        descriptionParams.topMargin = p.y/32;

       // need top margin
        description.setId(View.generateViewId());

        editDescription = new EditText(getActivity());
        container.addView(editDescription);
        editDescription.setGravity(Gravity.LEFT);
        editDescription.setGravity(Gravity.TOP);
        RelativeLayout.LayoutParams editDescriptionParams = (RelativeLayout.LayoutParams) editDescription.getLayoutParams();
        editDescriptionParams.height = p.y/3;
        editDescriptionParams.width = p.x * 15/16;
        editDescriptionParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        editDescriptionParams.addRule(RelativeLayout.BELOW, description.getId());
       // editDescriptionParams.topMargin = p.y/ constant;
        editDescriptionParams.leftMargin = p.x/32;
        editDescriptionParams.topMargin = p.y/32;
        editDescription.setBackgroundResource(R.drawable.roundedlayout);
        editDescription.setId(View.generateViewId());
        editDescription.setTag("desc");
        editDescription.setHint("Event Description");

        Button next = new Button(getActivity());
        container.addView(next);
        next.setTextColor(Color.WHITE);
        next.setText("Next");
        next.setBackgroundResource(R.drawable.bluerounded);
        RelativeLayout.LayoutParams nextParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        nextParams.height = p.y/15;
        nextParams.width = p.x/4;
        nextParams.rightMargin = p.x/32;
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                toEventCreationTwo(v);
            }
        });



        super.onStart();
    }
    public void toEventCreationTwo(View view){




        // Update event to add name and description
        TextView nameView = (TextView) container.findViewWithTag("name");
        TextView descView = (TextView) container.findViewWithTag("desc");
        String name = nameView.getText().toString();
        String desc = descView.getText().toString();

        if(name.length() > 0){
            editName.setBackgroundResource(R.drawable.roundedlayout);
        }
        if(desc.length() > 0)
            editDescription.setBackgroundResource(R.drawable.roundedlayout);

        if(name.length() <=0 && desc.length() <=0){
            editName.setBackgroundResource(R.drawable.error);
            editDescription.setBackgroundResource(R.drawable.error);
            return;
        }

        if(name.length() <= 0){
            editName.setBackgroundResource(R.drawable.error);

            return;
        }

        if(desc.length() <= 0){
            editDescription.setBackgroundResource(R.drawable.error);

            return;
        }



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getActivity(), "Fatal error: no user", Toast.LENGTH_SHORT).show();
            return;
        }
        UserCreateEvent activity = (UserCreateEvent) getActivity();
        activity.event.name = name;
        activity.event.description = desc;



        Fragment fragment = new UserPickDateAndTime();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
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
