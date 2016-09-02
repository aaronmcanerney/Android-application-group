package com.example.aaron.fragmenttest;

import android.net.Uri;

/**
 * Created by aaron_000 on 8/29/2016.
 */
public class Friend {
    private Uri uri;
    private String name;
    private String location;
    private String uid;

    public Friend(){
        uri = null;
        name = "";
        location = "";
        uid = "";
    }
    public Friend(Uri uri, String name, String location, String uid){
        this.uri = uri;
        this.name = name;
        this.location = location;
        this.uid = uid;

    }
    public Friend(Uri uri){
        this.uri = uri;
    }

    public Uri getUri(){
        return uri;
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public String getUid() { return uid; }
}
