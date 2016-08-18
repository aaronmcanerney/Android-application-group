package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

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

/**
 * Created by aaron_000 on 8/15/2016.
 */
public class Splash extends Activity {

    private static final int REQUIRED_GOOGLE_PLAY_SERVICES_VERSION = 9256000;
    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10MB

    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseStorage mFileStorage;

    private boolean splashTimeoutFinished = false;
    private boolean authenticationDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ImageView img  = (ImageView) findViewById(R.id.splash);
        Display d = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        img.getLayoutParams().height = p.y;
        img.getLayoutParams().width = p.x;

        // Insert users into database
        //mDatabase.child("users").child("eLWBEKBOkiTos0duJsnKxc2vzTC3").child("displayName").setValue("William Hairfield");
        //mDatabase.child("users").child("tCcyN9OMXISZQwLborMQTVqqUXm1").child("displayName").setValue("Thomas Jacky");
        //mDatabase.child("users").child("n00esV6XBKQaWeGOtPS271HYyED2").child("displayName").setValue("Administrator");

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    splashTimeoutFinished = true;
                    if (authenticationDone) {
                        // Just go to main activity
                        Intent intent  =  new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        // Login as user (if authenticated); otherwise, login
        authenticateUser();
    }

    public void authenticateUser() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) loginAsAdmin();
        else authenticationDone = true;
    }

    public void loginAsAdmin() {
        // For now, authenticate with a generic user
        mAuth.signInWithEmailAndPassword("admin@gmail.com", "password")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (! task.isSuccessful()) {
                            alert("Firebase", "ERROR: Invalid username or password!");
                            return;
                        }

                        authenticationDone = true;
                        if (splashTimeoutFinished) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
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
