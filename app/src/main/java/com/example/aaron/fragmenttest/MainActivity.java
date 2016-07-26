package com.example.aaron.fragmenttest;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;
import java.util.Objects;

// Firebase imports

public class MainActivity extends FragmentActivity {

    private static final int REQUIRED_GOOGLE_PLAY_SERVICES_VERSION = 9256000;
    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    private static final long MAX_FILE_SIZE_MB = 1024 * 1024 * 10; //

    private boolean authenticated;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseStorage mFileStorage;

    public Bitmap profilePicture;
    public String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Authenticate with a generic user
        mAuth.signInWithEmailAndPassword("admin@gmail.com", "password")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (! task.isSuccessful()) {
                            alert("Firebase", "ERROR: Invalid username or password!");
                            return;
                        }

                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user == null) return;

                        // Set connections (users who aren't you)
                        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot child: snapshot.getChildren()) {
                                    String connectionId = child.getKey();
                                    addConnection(connectionId);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        // Get display name (if it exists)
                        String name = user.getDisplayName();
                        if (name == null || name.length() == 0) {
                            // Set display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("Administrator")
                                    .setPhotoUri(Uri.parse("https://scontent.fsnc1-1.fna.fbcdn.net/v/t1.0-9/13332832_10205404552187061_2395478814231725043_n.jpg?oh=0a394a19309cc0cb7ab3c0cf4f720e8f&oe=57EA48B5"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                alert("Firebase", "User profile updated.");
                                            }
                                        }
                                    });
                        }
                        displayName = name;

                        // Create event
                        String uid = user.getUid();
                        Event e = new Event(uid, "Boys Night In", "Wii Bowling");
                        e.addConnection("Darth Vader");
                        e.addConnection("Luke Skywalker");
                        e.addConnection("Han Solo");
                        e.push();

                        // Set profile picture
                        mFileStorage = FirebaseStorage.getInstance();
                        StorageReference storageRef = mFileStorage.getReferenceFromUrl(FIREBASE_STORAGE_BUCKET);
                        storageRef.child("profile-pictures/" + uid + ".jpg").getBytes(MAX_FILE_SIZE_MB)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Save profile picture
                                        profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
                                        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                alert("Firebase", "Failed to download profile picture!");
                            }
                        });
                    }
                });
    }

    public void addConnection(String connectionId) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;
        String uid = user.getUid();
        if (uid.equals(connectionId)) return;
        mDatabase.child("connections/" + uid + "/" + connectionId).setValue("");
    }

    /*public void signIn(View view) {
        AutoCompleteTextView emailView = (AutoCompleteTextView) findViewById(R.id.email);
        EditText passwordView = (EditText) findViewById(R.id.password);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (! supportsFirebase()) return;

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (! task.isSuccessful()) {
                            alert("Firebase", "ERROR: Invalid username or password!");
                        }
                    }
                });
    }

    public void register(View view) {
        AutoCompleteTextView emailView = (AutoCompleteTextView) findViewById(R.id.email);
        EditText passwordView = (EditText) findViewById(R.id.password);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (! supportsFirebase()) return;

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (! task.isSuccessful()) {
                            alert("Firebase", "ERROR: Email in use or password too weak!");
                        }
                    }
                });
    }

    public void signOut(View view) {
        if (getGooglePlayServicesVersion() < REQUIRED_GOOGLE_PLAY_SERVICES_VERSION) return;
        FirebaseAuth.getInstance().signOut();
    }*/

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

    public boolean supportsFirebase() {
        return getGooglePlayServicesVersion() >= REQUIRED_GOOGLE_PLAY_SERVICES_VERSION;
    }

    public int getGooglePlayServicesVersion() {
        try {
            return getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return HomeProfile.newInstance("FirstFragment, Instance 1");
                case 1:
                    return EventCreation.newInstance("SecondFragment, Instance 1");
                case 2:
                    return MyCalendar.newInstance("ThirdFragment, Instance 1", "Extra string");

                default:
                    //return HomeProfile.newInstance("ThirdFragment, Default");
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}