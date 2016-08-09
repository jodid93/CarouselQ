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
    private String userName;
    private int skipvotes;
    private String userHash;

    public Track(String uri, String name, int dur, String artist, int id, String user, int skipvotes, String userHash){
        this.uri = uri;
        this.trackName = name;
        this.trackDuration = dur;
        this.artistName = artist;
        this.id = id;
        this.userName = user;
        this.skipvotes = skipvotes;
        this.userHash = userHash;
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

    public String getUserName(){ return this.userName; }

    public int getSkipvotes() { return this.skipvotes; }

    public String getuserHash() { return this.userHash; }
}
