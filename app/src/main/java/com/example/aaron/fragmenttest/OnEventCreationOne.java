package com.example.aaron.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Display;
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


public class OnEventCreationOne extends Fragment {

    RelativeLayout enameRL;
    RelativeLayout descriptionRL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_on_event_creation_one
                , container, false);
        return v;
    }
    public static OnEventCreationOne newInstance(String text) {

        OnEventCreationOne f = new OnEventCreationOne();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onStart(){
        //add a border to the relative layout
        ShapeDrawable rectShapeDrawable = new ShapeDrawable();
        Paint paint = rectShapeDrawable.getPaint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        //format the name again
        SpannableString nameFormatted = new SpannableString("Event Name: ");
        nameFormatted.setSpan(new UnderlineSpan(), 0, nameFormatted.length(), 0);
        nameFormatted.setSpan(new StyleSpan(Typeface.BOLD), 0, nameFormatted.length(), 0);
        nameFormatted.setSpan(new StyleSpan(Typeface.ITALIC), 0, nameFormatted.length(), 0);
        //get the linear layout so everything is aligned vertically
        RelativeLayout rl = (RelativeLayout) this.getActivity().findViewById(R.id.event_creation_one_linear_layout);
        Display d = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        //create first rl
        enameRL = new RelativeLayout(this.getActivity());
        rl.addView(enameRL);
        //set the height and background
        enameRL.getLayoutParams().height = point.y/5;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) enameRL.getLayoutParams();
        params.topMargin = point.x /15;
        enameRL.setId(View.generateViewId());
        enameRL.requestLayout();
        enameRL.setBackground(rectShapeDrawable);
        enameRL.setBackgroundColor(Color.BLUE);
        //enameRL.setBackgroundResource(R.mipmap.back_layout);
        //event name and edit text added to relative layout
        TextView ename = new TextView(this.getActivity());
        ename.setTag("name");
        ename.setText(nameFormatted);
        EditText editName = new EditText(this.getActivity());
        enameRL.addView(ename);
        enameRL.addView(editName);

        //Create relative layout params for Event name
        RelativeLayout.LayoutParams pname = (RelativeLayout.LayoutParams) ename.getLayoutParams();
        pname.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        ename.setLayoutParams(pname);
        ename.setId(View.generateViewId());
        RelativeLayout.LayoutParams pEditName = (RelativeLayout.LayoutParams) editName.getLayoutParams();
        pEditName.addRule(RelativeLayout.BELOW, ename.getId());
        pEditName.width = point.x;
        pEditName.height = point.y /5;
        editName.setLayoutParams(pEditName);

        //Creating Relative layout for description
        /*RelativeLayout placeHolder = new RelativeLayout(this.getActivity());
        rl.addView(placeHolder);
        placeHolder.getLayoutParams().height = point.y/10;
        RelativeLayout.LayoutParams placeParam = (RelativeLayout.LayoutParams) placeHolder.getLayoutParams();
        placeParam.addRule(RelativeLayout.ALIGN_BASELINE);
        */

        descriptionRL = new RelativeLayout(this.getActivity());
        rl.addView(descriptionRL);
        descriptionRL.setBackgroundColor(Color.BLUE);
        descriptionRL.setBackground(rectShapeDrawable);
        //descriptionRL.setBackgroundColor(Color.GRAY);
        //set the height and background
        descriptionRL.getLayoutParams().height = point.y/3;
        TextView eDescription = new TextView(this.getActivity());
        eDescription.setText("Description: ");
        eDescription.setTag("desc");
        EditText editDescription = new EditText(this.getActivity());
        descriptionRL.addView(eDescription);
        descriptionRL.addView(editDescription);
        RelativeLayout.LayoutParams descriptionParams = (RelativeLayout.LayoutParams) eDescription.getLayoutParams();
        pname.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        eDescription.setLayoutParams(descriptionParams);
        eDescription.setId(View.generateViewId());
        RelativeLayout.LayoutParams editDescriptionParams = (RelativeLayout.LayoutParams) editDescription.getLayoutParams();
        editDescriptionParams.addRule(RelativeLayout.BELOW, eDescription.getId());
        editDescriptionParams.width = point.x;
        editDescriptionParams.height = point.y /3;
        editDescription.setLayoutParams(editDescriptionParams);
        enameRL.setId(View.generateViewId());
        RelativeLayout.LayoutParams alignDescription = (RelativeLayout.LayoutParams) descriptionRL.getLayoutParams();
       // alignDescription.addRule(RelativeLayout.ABOVE, enameRL.getId());
        alignDescription.addRule(RelativeLayout.BELOW, enameRL.getId());
        alignDescription.topMargin = point.y/10;
        descriptionRL.setLayoutParams(alignDescription);


        Button button = new Button(this.getActivity());
        rl.addView(button);
        button.setText("Next");
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                toEventCreationTwo(v);
            }
        });
        RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        button.setLayoutParams(buttonParams);



        super.onStart();
    }
    public void toEventCreationTwo(View view){
        Fragment fragment = new OnEventCreationTwo();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();

        // Update event to add name and description
        TextView nameView = (TextView) enameRL.findViewWithTag("name");
        TextView descView = (TextView) descriptionRL.findViewWithTag("desc");
        String name = nameView.getText().toString();
        String desc = (String) descView.getText();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getActivity(), "Fatal error: no user", Toast.LENGTH_SHORT).show();
            return;
        }
        OnEventCreation activity = (OnEventCreation) getActivity();
        activity.event.setName(name);
        activity.event.setDescription(desc);
        activity.event.push();
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