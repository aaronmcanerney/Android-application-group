package com.example.aaron.fragmenttest;

/**
 * Created by rogowski on 9/12/2016.
 */
public class FirebaseWaitLoader {

    private int numLoaded = 0;
    private int numToLoad;

    public FirebaseWaitLoader(int numToLoad) {
        this.numToLoad = numToLoad;
    }

    public void update() {
        numLoaded++;
    }

    public boolean done() {
        return numLoaded == numToLoad;
    }
}
