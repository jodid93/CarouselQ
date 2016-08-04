package com.example.notandi.carouselq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.database.DBConnector;
import com.example.notandi.carouselq.database.JSONHelper;
import com.example.notandi.carouselq.queueController.Album;
import com.example.notandi.carouselq.queueController.Track;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivityAlbum extends AppCompatActivity {

    private ListView mArtistAlbumList;
    private DBConnector mConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity_album);

        this.mArtistAlbumList = (ListView) findViewById(R.id.artist_album_list);
        this.mConnector = DBConnector.getInstance();

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        System.out.println("-------------------------"+query+"---------------query---------------");

        JSONObject albumData =  mConnector.getAlbumTracks(query);
        ArrayList<Album> albums = JSONHelper.getAlbumsFromArtist(albumData);
        ListAdapterAlbum adapter = new ListAdapterAlbum(albums, SearchActivityAlbum.this);

        mArtistAlbumList.setAdapter(adapter);


    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, SearchActivityAlbum.class);
        return i;
    }
}
