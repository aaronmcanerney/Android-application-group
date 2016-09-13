package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
import java.util.NoSuchElementException;

public class Groups extends AppCompatActivity {
    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    private ArrayList<Friend> friends;
    private int numFriendsLoaded;
    private int numFriendsToLoad;
    ListView hold;
    GroupAdapter groupAdapter;

    HashMap<Friend, Boolean> selection;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);


        selection = new HashMap<>();

        // Set connections (users who aren't you)
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        String uid = user.getUid();


        Display d = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final Point p = getDisplaySize(d);


        mDatabase.child("connections/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                numFriendsLoaded = 0;
                numFriendsToLoad = (int) snapshot.getChildrenCount();
                friends = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) {


                    String connectionId = child.getKey();
                    loadPublicInfo(connectionId);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadPublicInfo(String connectionId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users/" + connectionId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> map = new HashMap<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    // Get all data from firebase
                    String key = child.getKey();
                    Object value = child.getValue(Object.class);
                    map.put(key, value);
                }

                String profilePictureURI = (String) map.get("profilePictureURI");
                String displayName = (String) map.get("displayName");
                String location = (String) map.get("location");
                String connectionId = snapshot.getKey();

                Friend temp = new Friend(Uri.parse(profilePictureURI), displayName, location, connectionId);
                friends.add(temp);
                numFriendsLoaded++;

                selection.put(temp, false);


                if (numFriendsLoaded == numFriendsToLoad) addFriends();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addFriends(){
        hold = (ListView) findViewById(R.id.friends_list);
        hold.setBackgroundColor(Color.parseColor("#d6dbe1"));

        final Friend[] temp =  friends.toArray(new Friend[friends.size()]);
        List<Friend> friendsList = Arrays.asList(temp);

        EditText search = (EditText) findViewById(R.id.inputSearch);
        groupAdapter = new GroupAdapter(this,friendsList);

        Button button = (Button) findViewById(R.id.create_group);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateGroup(v);
            }
        });

        hold.setAdapter(groupAdapter);


        hold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                if(!selection.get(parent.getItemAtPosition(position))){
                    selection.put((Friend)parent.getItemAtPosition(position), true);
                    view.setBackgroundResource(R.drawable.selected);
                }
                else{
                    selection.put((Friend)parent.getItemAtPosition(position), false);
                    view.setBackgroundResource(0);

                }


            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                int textLength = cs.length();
                ArrayList<Friend> tempArrayList = new ArrayList<>();
                for(Friend c: temp){
                    if (textLength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(cs.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }
                }
                groupAdapter = new GroupAdapter(Groups.this, tempArrayList);
                hold.setAdapter(groupAdapter);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }




    public void alert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
        Display d = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
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


    public void CreateGroup(View view){
        Group group = new Group();
        group.connectionIds = new ArrayList<>();
        for(Friend key : selection.keySet()){
            if(selection.containsKey(key)){
                if(selection.get(key))
                    group.connectionIds.add(key.getName());
            }
        }

        // Submit group to firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    }


}
