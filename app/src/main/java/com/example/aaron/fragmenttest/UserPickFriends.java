package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.NoSuchElementException;


public class UserPickFriends extends Fragment{
    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    public static final int colNum = 4;
    GridLayout gridLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_user_pick_friends
                    , container, false);
            return v;
        }




        public static UserPickFriends newInstance(String text) {

            UserPickFriends f = new UserPickFriends();
            Bundle b = new Bundle();
            b.putString("msg", text);

            f.setArguments(b);

            return f;
        }




        public void onStart(){

            // Set connections (users who aren't you)
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;
            String uid = user.getUid();


            final Display d = ((WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            final Point p = getDisplaySize(d);
            gridLayout = (GridLayout) this.getActivity().findViewById(R.id.fragment_connections_container);
            gridLayout.setBackgroundColor(Color.parseColor("#d6dbe1"));
            gridLayout.setColumnCount(colNum);



            mDatabase.child("connections/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
                int count = 0;

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {


                        String connectionId = child.getKey();
                        loadConnectionPicture(connectionId, d);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            super.onStart();
        }




    public void loadConnectionPicture(String connectionId, final Display d) {
        FirebaseStorage mFileStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mFileStorage.getReferenceFromUrl(FIREBASE_STORAGE_BUCKET);
        storageRef.child("profile-pictures/" + connectionId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                addImageButton(uri ,d);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void addImageButton(Uri profilePictureURI, Display d) {



        Point p = getDisplaySize(d);
        int x = p.x * 30 / 100;
        //int y = point.y * 15 / 100;
        int xMargin = p.x * 3 / 100;
        int yMargin = p.y * 3 / 100;



        //RelativeLayout temp = new RelativeLayout(this);
        RelativeLayout temp = new RelativeLayout(this.getActivity());
        temp.setBackgroundResource(R.drawable.roundedlayout);
        gridLayout.addView(temp);
        GridLayout.LayoutParams tempParams = (GridLayout.LayoutParams) temp.getLayoutParams();
        tempParams.width = x;
        tempParams.height = x;
        tempParams.leftMargin = xMargin;
        tempParams.topMargin = yMargin;
        //temp.setBackgroundResource(R.drawable.bluerounded);
        ImageView button = new ImageView(this.getActivity());

        //button.setClickable(true);
        temp.addView(button);
        // Point p = scaleImage(button);
        Picasso.with(this.getActivity()).load(profilePictureURI).resize(p.x/4, p.y * 2 / 7).into(button);
        Picasso.with(this.getActivity()).load(profilePictureURI).resize(p.x/4 - 15, p.x/4 - 15).transform(new CircleTransform()).into(button);
        //button.getBackground().setAlpha(0);
        RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        buttonParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(getActivity(), "Hello you clicked your friend", Toast.LENGTH_LONG).show();
                v.setBackgroundResource(R.drawable.greenborder);
            }
        });
        Button next  = (Button) this.getActivity().findViewById(R.id.to_user_submit_event);
        next.setBackgroundResource(R.drawable.bluerounded);
        next.setTextColor(Color.WHITE);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toSubmitEvent(v);
            }
        });
    }

    public void alert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private Point scaleImage(ImageView view) throws NoSuchElementException {
        Display d = ((WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(10, 10, 5, 5);
        params.width = p.x/3 - 15;
        params.height = p.y * 2 / 7 ;
        view.setLayoutParams(params);
        return p;
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

    public void toSubmitEvent(View view){
        Fragment fragment = new UserSubmitEvent();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
    }

}