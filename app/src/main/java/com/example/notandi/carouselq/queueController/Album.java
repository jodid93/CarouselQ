package com.example.notandi.carouselq.queueController;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class Album {

    private String linkToTracks;
    private String name;
    private String band_name;
    private int id;

    public Album(String link, String name, int id){
        this.linkToTracks = link;
        this.name = name;
        this.id = id;
    }

    //getters

    public String getLinkToTracks(){
        return this.linkToTracks;
    }

    public String getName(){
        return this.name;
    }

    public String getBand_name() {return this.band_name; }

    public int getId() {
        return this.id;
    }
}
