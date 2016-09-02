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
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.List;


public class UserPickFriends extends Fragment{

    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    public static final int colNum = 4;
    GridLayout gridLayout;
    private ArrayList<Friend> friends;
    private int numFriendsLoaded;
    private int numFriendsToLoad;
    ListView hold;

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

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;
            String uid = user.getUid();


            Display d = ((WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            final Point p = getDisplaySize(d);


            mDatabase.child("connections/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    numFriendsLoaded = 0;
                    numFriendsToLoad = (int) snapshot.getChildrenCount();
                    friends = new ArrayList<>();

                    for (DataSnapshot child : snapshot.getChildren()) {


                        String connectionId = child.getKey();
                        loadConnectionPicture(connectionId);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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


            super.onStart();
        }

    public void loadConnectionPicture(String connectionId) {
        FirebaseStorage mFileStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mFileStorage.getReferenceFromUrl(FIREBASE_STORAGE_BUCKET);
        storageRef.child("profile-pictures/" + connectionId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //addImageButton(uri);
                Friend temp = new Friend(uri);
                friends.add(temp);
                numFriendsLoaded++;
                if (numFriendsLoaded == numFriendsToLoad) addFriends();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void addFriends(){
        hold = (ListView) getActivity().findViewById(R.id.friends_list);
        hold.setBackgroundColor(Color.parseColor("#d6dbe1"));

        Friend[] temp =  friends.toArray(new Friend[friends.size()]);
        List<Friend> friendsList = Arrays.asList(temp);

        hold.setAdapter(new UserPickFriendsAdapter(this.getActivity() ,friendsList));
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