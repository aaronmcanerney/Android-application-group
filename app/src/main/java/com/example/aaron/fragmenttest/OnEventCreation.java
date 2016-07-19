package com.example.aaron.fragmenttest;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OnEventCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_event_creation);
        Fragment fragment = new OnEventCreationOne();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).commit();

    }

}
