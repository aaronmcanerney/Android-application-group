package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Connections extends AppCompatActivity {

    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    private ArrayList<Friends> friends;
    private int numFriendsLoaded;
    private int numFriendsToLoad;
    ListView hold;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

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
                        loadConnectionPicture(connectionId);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadConnectionPicture(String connectionId) {
        FirebaseStorage mFileStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mFileStorage.getReferenceFromUrl(FIREBASE_STORAGE_BUCKET);
        storageRef.child("profile-pictures/" + connectionId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //addImageButton(uri);
                    Friends temp = new Friends(uri);
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
        hold = (ListView) findViewById(R.id.friends_list);
        hold.setBackgroundColor(Color.parseColor("#d6dbe1"));

        Friends[] temp =  friends.toArray(new Friends[friends.size()]);
        List<Friends> friendsList = Arrays.asList(temp);

        hold.setAdapter(new FriendsAdapter(this ,friendsList));
    }


    /*
    public void addImageButton(Uri profilePictureURI) {


        Display d = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        int x = p.x * 30 / 100;
        //int y = point.y * 15 / 100;
        int xMargin = p.x * 3 / 100;
        int yMargin = p.y * 3 / 100;


        GridLayout gridLayout = (GridLayout) this.findViewById(R.id.connections_container);
        gridLayout.setColumnCount(colNum);
        //RelativeLayout temp = new RelativeLayout(this);
        RelativeLayout temp = new RelativeLayout(this);
        temp.setBackgroundResource(R.drawable.roundedlayout);
        gridLayout.addView(temp);
        GridLayout.LayoutParams tempParams = (GridLayout.LayoutParams) temp.getLayoutParams();
        tempParams.width = x;
        tempParams.height = x;
        tempParams.leftMargin = xMargin;
        tempParams.topMargin = yMargin;
        //temp.setBackgroundResource(R.drawable.bluerounded);
        ImageView button = new ImageView(this);

        //button.setClickable(true);
        temp.addView(button);
       // Point p = scaleImage(button);
        Picasso.with(this).load(profilePictureURI).resize(p.x/4, p.y * 2 / 7).into(button);
        Picasso.with(this).load(profilePictureURI).resize(p.x/4 - 15, p.x/4 - 15).transform(new CircleTransform()).into(button);
        //button.getBackground().setAlpha(0);

        RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        buttonParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Connections.this, "Hello you clicked your friend", Toast.LENGTH_LONG).show();
            }
        });
    }
    */

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


}
