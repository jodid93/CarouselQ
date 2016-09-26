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
 * 
 * done: mQcontroller doesn't seem to initialize when running on the phone. -- held að vandamálið liggi hjá samsung galaxy s4. virkar á öllum öðrum tækjum
 * todo: add button controll for the admin of the queue and disable all other player activities for non admins
 * done: fix the get queues method on the backend. something is terribly broken and is held together with black magic -- on it -- UPDATE: REMOVE AND RE WRITE. THIS IS SHIIIIIT!!! DONT EVER WRITE CODE AGAIN WHILE DRINKING YOU FUCKING IDIOT! -- final comment. backend is fixed and needs to be connected again to the front end (minor tweeks, mainly the same response from server)
 * todo: create a better view for the lists in the list adapters. 
 *       add downvote counts, user etc.
 * todo: call the updatequeue method on instantiation of the mainActivity to get an initial queue
 * todo: set up cloud services
 * todo: FIND OUT WHY THE FUCK EVERYTHING CRASHES ALL THE FUCKING TIME!!!
 * todo: fix the theme and look of the app
 * todo: add many many more try catch for the database functions so that if it crashes the queueid (or the userinfo object for that matter)isn't reset -- done some
 * todo: remember that this is a hobby and try not to go overboard or loose your mind
 * todo: find out better connections between queues on different phones, that is, update queue display
 * todo: reverse the order in which the songs queue on the backend -- done
 */
public class QController {

    private static QController instance = null;
    private static Player mPlayer;
    private static ArrayList<Track> tracksList;
    private static Context context;
    private static DBConnector mConnector;
    private static UserInfo mUserInfo;

    protected QController(Player player,Context context){
        this.mPlayer = player;
        tracksList = new ArrayList<Track>();
        this.context = context;
        this.mConnector = DBConnector.getInstance();
        this.mUserInfo = UserInfo.getInstance();
    }

    protected QController(Context context){
        this.mPlayer = null;
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

    public static QController getInstance(Context context) {
        if(instance == null) {
            instance = new QController(context);
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
        if(queue == null) return;
        this.tracksList = queue;
        ListAdapterQueue qAdapter = new ListAdapterQueue(tracksList,context);
        MainActivity.mSongQueue.setAdapter(qAdapter);
        //kalla á gagnagrunn og uppfæra tracklist
    }

    public void updateQueue(ArrayList<Track> queue){

        if(queue == null) return;
        this.tracksList = queue;
        ListAdapterQueue qAdapter = new ListAdapterQueue(tracksList,context);
        MainActivity.mSongQueue.setAdapter(qAdapter);
        //kalla á gagnagrunn og uppfæra tracklist
    }

    public void playNextTrack(){
        //nota mPlayer til að kalla á næsta lag
        if(tracksList.size() >= 1 && mUserInfo.getOwner()){
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

        ArrayList<Track> queue = mConnector.addSongToQueueAndUpdate(mUserInfo.getHashedUserName(),track.getUri(),track.getTrackName(),track.getArtistName(),track.getTrackDuration(), mUserInfo.getQueueID());
        //temp local functionality
        updateQueue(queue);
    }

    public void playIfFirst(){
        if(mUserInfo.getOwner()){
            mPlayer.getPlayerState(new PlayerStateCallback() {
                @Override
                public void onPlayerState(PlayerState playerState) {
                    if(tracksList.size() == 1 && !playerState.playing){
                        playNextTrack();
                    }
                }
            });
        }
    }

    public ArrayList<Track> getQueue(){ return this.tracksList; }
}
