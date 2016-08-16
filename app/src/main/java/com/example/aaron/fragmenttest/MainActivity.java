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

// Firebase imports

public class MainActivity extends FragmentActivity {
    private static final int REQUIRED_GOOGLE_PLAY_SERVICES_VERSION = 9256000;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
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
                    return HomeProfile.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}