package com.example.aaron.fragmenttest;

import android.net.Uri;

/**
 * Created by aaron_000 on 8/29/2016.
 */
public class Friends {
    private Uri uri;
    private String name;
    private String location;

    public Friends(){
        uri = null;
        name = "";
        location = "";
    }
    public Friends(Uri uri, String name, String location ){
        this.uri = uri;
        this.name = name;
        this.location = location;

    }
    public Friends(Uri uri){
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
}
