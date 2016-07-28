package com.example.notandi.carouselq.queueController;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class Artist {

    private String linkToAlbums;
    private String name;

    public Artist(String link, String name){
        this.linkToAlbums = link;
        this.name = name;
    }

    //getters

    public String getLinkToAlbums(){
        return this.linkToAlbums;
    }

    public String getName(){
        return this.name;
    }
}
