package com.example.notandi.carouselq.queueController;

import android.content.Context;

import com.example.notandi.carouselq.activities.ListAdapterQueue;
import com.example.notandi.carouselq.activities.MainActivity;
import com.example.notandi.carouselq.database.DBConnector;
import com.example.notandi.carouselq.database.JSONHelper;
import com.example.notandi.carouselq.users.UserInfo;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;

import org.json.JSONObject;

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
    private DBConnector mConnector;
    private UserInfo mUserInfo;

    protected QController(Player player,Context context){
        this.mPlayer = player;
        tracksList = new ArrayList<Track>();
        this.context = context;
        this.mConnector = DBConnector.getInstance();
        this.mUserInfo = UserInfo.getInstance();
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

        ArrayList<Track> queue = mConnector.getQueue(this.mUserInfo.getQueueID());
        this.tracksList = queue;
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
        if(tracksList.size() >= 1) {
            mConnector.removeSong(mUserInfo.getHashedUserName(), tracksList.get(0).getUri());
            tracksList.remove(0);
        }

        updateQueue();
    }

    public void addSongToQueue(Track track){
        //kalla á gagnagrunn til að setja track inn í db
        //þarf að kalla líka á update queue

        mConnector.addSongToQueue(mUserInfo.getHashedUserName(),track.getUri(),track.getTrackName(),track.getArtistName(),track.getTrackDuration());
        //temp local functionality
        updateQueue();
    }

    public void playIfFirst(){

        mPlayer.getPlayerState(new PlayerStateCallback() {
            @Override
            public void onPlayerState(PlayerState playerState) {
                if(tracksList.size() == 1 && !playerState.playing){
                    playNextTrack();
                }
            }
        });


    }

    public ArrayList<Track> getQueue(){ return this.tracksList; }
}
