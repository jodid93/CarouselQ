package com.example.notandi.carouselq.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.database.DBConnector;
import com.example.notandi.carouselq.database.JSONHelper;
import com.example.notandi.carouselq.queueController.QController;
import com.example.notandi.carouselq.queueController.Track;
import com.example.notandi.carouselq.users.UserInfo;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private DBConnector backendConnector;
    private static final String CLIENT_ID = "63f59894866d46648c5cd2975feea347";

    private static final String REDIRECT_URI = "shit-eg-vona-ad-thetta-virki://callback";

    private static final int REQUEST_CODE = 1337;

    public static ListView mSongQueue;
    private TextView mQueueName;
    private Button mAddSong;
    private UserInfo user;
    private QController mController;

    private Player mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the context class with the context of this activity to use it later
        context init = context.getInstance();
        init.init(this.getApplicationContext());


        backendConnector = DBConnector.getInstance();
        //backendConnector.testConnection();

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        this.mSongQueue = (ListView) findViewById(R.id.songQueue);
        this.mQueueName = (TextView) findViewById(R.id.queueName);
        this.mAddSong = (Button) findViewById(R.id.addSong);
        this.user = UserInfo.getInstance();


        this.mQueueName.setText("Queue Id: "+this.user.getQueueID());


        //ListAdapterQueue qAdapter = new ListAdapterQueue(mController.getQueue(), MainActivity.this);
        //mSongQueue.setAdapter(qAdapter);

        this.mAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = SearchActivityMain.newIntent(MainActivity.this);
                startActivityForResult(i, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {

                    @Override
                    public void onInitialized(Player player) {
                        mPlayer = player;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addPlayerNotificationCallback(MainActivity.this);
                        System.out.println("hallo");
                        //mPlayer.play("spotify:track:1vMg9rNGFzdPRBfsp8KSxd");

                        mController = QController.getInstance(mPlayer, MainActivity.this);
                        System.out.println("----------------------------------- testa spotify");

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
            else{
                System.out.println("-----------------------------------nae ekki ad tengjast spotify 1 --------- "+response.getType());
            }
        }else{
            System.out.println("-----------------------------------nae ekki ad tengjast spotify 2");
        }

    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, MainActivity.class);
        return i;
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

        Log.d("MainActivity", "Playback event received: " + eventType.name());

        if(eventType.name().equals("PAUSE")){
            mController.popFirst();
            mController.playNextTrack();
        }
        if(eventType.name().equals("PLAY")){

        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
        System.out.println("er nuna ad eydinleggja main activity");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to leave this queue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backendConnector.makeUserInactive(user.getHashedUserName());
                        SharedPreferences settings = getSharedPreferences("prefrences", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        editor.commit();
                        MainActivity.this.onDestroy();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

    }
}
