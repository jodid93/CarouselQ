package com.example.notandi.carouselq.queueController;

import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class QController {

    private Player mPlayer;
    private ArrayList<Track> tracksList;

    public QController(Player player){
        this.mPlayer = player;
        tracksList = new ArrayList<Track>();
    }

    public void updateQueue(){
        //kalla á gagnagrunn og uppfæra tracklist
    }

    public void playNextTrack(){
        //nota mPlayer til að kalla á næsta lag

        mPlayer.play(tracksList.get(0).getUri());
    }

    public void addSongToQueue(ArrayList<Track> tracks){
        //kalla á gagnagrunn til að setja track inn í db
        //þarf að kalla líka á update queue

        for (int i = 0; i < tracks.size(); i++) {
            tracksList.add(tracks.get(i));
        }

        playNextTrack();
    }
}
