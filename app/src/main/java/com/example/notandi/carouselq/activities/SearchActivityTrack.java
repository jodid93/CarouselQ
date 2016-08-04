package com.example.notandi.carouselq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.database.DBConnector;
import com.example.notandi.carouselq.database.JSONHelper;
import com.example.notandi.carouselq.queueController.QController;
import com.example.notandi.carouselq.queueController.Track;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivityTrack extends AppCompatActivity {

    private ListView mAlbumTrackList;
    private DBConnector mConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity_track);

        this.mAlbumTrackList = (ListView) findViewById(R.id.album_track_list);
        this.mConnector = DBConnector.getInstance();

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        System.out.println("-------------------------"+query+"---------------query---------------");

        JSONObject trackData =  mConnector.getAlbumTracks(query);
        ArrayList<Track> tracks = JSONHelper.getTracks(trackData);
        ListAdapterTrack adapter = new ListAdapterTrack(tracks, SearchActivityTrack.this);

        mAlbumTrackList.setAdapter(adapter);


    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, SearchActivityTrack.class);
        return i;
    }
}
