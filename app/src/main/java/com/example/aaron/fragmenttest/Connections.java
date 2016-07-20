package com.example.aaron.fragmenttest;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.NoSuchElementException;

public class Connections extends AppCompatActivity {

    private static final String FIREBASE_STORAGE_BUCKET = "gs://unisin-1351.appspot.com";
    public static final int colNum = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        FirebaseStorage mFileStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mFileStorage.getReferenceFromUrl(FIREBASE_STORAGE_BUCKET);
        for (int i = 1; i < 3; i++) {
            storageRef.child("profile-pictures/friend" + Integer.toString(i) + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    addImageButton(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
    }

    public void addImageButton(Uri profilePictureURI) {
        GridLayout gridLayout = (GridLayout) this.findViewById(R.id.connections_container);
        gridLayout.setColumnCount(colNum);
        ImageButton button = new ImageButton(this);
        Picasso.with(this).load(profilePictureURI).into(button);
        //button.setImageResource(R.mipmap.connect);
        button.setScaleType(ImageView.ScaleType.CENTER);
        button.setClickable(true);
        gridLayout.addView(button);
        scaleImage(button);
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

    private void scaleImage(ImageView view) throws NoSuchElementException {
        Display d = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = getDisplaySize(d);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(10, 10, 5, 5);
        params.width = p.x/3 - 15;
        params.height = p.y * 2 / 7 ;
        view.setLayoutParams(params);
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
