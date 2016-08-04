package com.example.notandi.carouselq.queueController;

import android.view.View;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class Track {

    private String uri;
    private String trackName;
    private int trackDuration;
    private String artistName;
    private int id;

    public Track(String uri, String name, int dur, String artist, int id){
        this.uri = uri;
        this.trackName = name;
        this.trackDuration = dur;
        this.artistName = artist;
        this.id = id;
    }

    ///////////////////////////////
    // getters
    //////////////////////////////

    public String getUri(){
        return this.uri;
    }

    public String getTrackName(){
        return this.trackName;
    }

    public String getArtistName(){
        return this.artistName;
    }

    public int getTrackDuration(){
        return this.trackDuration;
    }

    public int getId(){ return this.id; }
}
