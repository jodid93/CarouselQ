package com.example.notandi.carouselq.queueController;

import android.content.Context;

import com.example.notandi.carouselq.activities.ListAdapterQueue;
import com.example.notandi.carouselq.activities.MainActivity;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jósúa on 28-Jul-16.
 */
public class QController {

    private static QController instance = null;
    private Player mPlayer;
    private ArrayList<Track> tracksList;
    private Context context;

    protected QController(Player player,Context context){
        this.mPlayer = player;
        tracksList = new ArrayList<Track>();
        this.context = context;
    }

    public static QController getInstance(Player player, Context context) {
        if(instance == null) {
            instance = new QController(player, context);
        }
        return instance;
    }

    public static QController getInstance(){
        if(instance != null){
            return instance;
        }
        return null;
    }

    public void updateQueue(){
        ListAdapterQueue qAdapter = new ListAdapterQueue(tracksList,context);
        MainActivity.mSongQueue.setAdapter(qAdapter);
        //kalla á gagnagrunn og uppfæra tracklist
    }

    public void playNextTrack(){
        //nota mPlayer til að kalla á næsta lag
        if(tracksList.size() >= 1){
            mPlayer.play(tracksList.get(0).getUri());
        }
    }

    public void popFirst(){
        tracksList.remove(0);
        updateQueue();
    }

    public void addSongToQueue(Track track){
        //kalla á gagnagrunn til að setja track inn í db
        //þarf að kalla líka á update queue

        //temp local functionality
        tracksList.add(track);
        updateQueue();
    }

    public void playIfFirst(){
        if(tracksList.size() == 1){
            playNextTrack();
        }
    }

    public ArrayList<Track> getQueue(){ return this.tracksList; }
}
