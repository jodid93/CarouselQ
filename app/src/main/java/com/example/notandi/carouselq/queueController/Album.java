package com.example.notandi.carouselq.queueController;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class Album {

    private String linkToTracks;
    private String name;

    public Album(String link, String name){
        this.linkToTracks = link;
        this.name = name;
    }

    //getters

    public String getLinkToTracks(){
        return this.linkToTracks;
    }

    public String getName(){
        return this.name;
    }
}
