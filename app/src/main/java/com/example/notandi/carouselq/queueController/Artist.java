package com.example.notandi.carouselq.queueController;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class Artist {

    private String linkToAlbums;
    private String name;
    private int id;

    public Artist(String link, String name, int id){
        this.linkToAlbums = link;
        this.name = name;
        this.id = id;
    }

    //getters

    public String getLinkToAlbums(){
        return this.linkToAlbums;
    }

    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }
}
