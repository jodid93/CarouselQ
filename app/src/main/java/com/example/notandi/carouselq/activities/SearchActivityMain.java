package com.example.notandi.carouselq.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.database.DBConnector;
import com.example.notandi.carouselq.database.JSONHelper;
import com.example.notandi.carouselq.queueController.Album;
import com.example.notandi.carouselq.queueController.Artist;
import com.example.notandi.carouselq.queueController.QController;
import com.example.notandi.carouselq.queueController.Track;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivityMain extends AppCompatActivity {

    private QController mQcontroller;
    private DBConnector mConnector;
    private ListView mTrackView;
    private ListView mAlbumView;
    private ListView mArtistView;
    private TextView mSearchInput;
    private Button mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.mQcontroller = QController.getInstance();
        this.mConnector = DBConnector.getInstance();

        this.mTrackView = (ListView) findViewById(R.id.TrackList);
        this.mAlbumView = (ListView) findViewById(R.id.AlbumList);
        this.mArtistView = (ListView) findViewById(R.id.ArtistList);

        this.mSearchInput = (TextView) findViewById(R.id.serchInput);
        this.mSearchBtn = (Button) findViewById(R.id.btnSearch);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchInput = mSearchInput.getText().toString();
                searchInput = searchInput.replaceAll(" ", "+");
                if(searchInput == null ){ //TODO parse input to not allow spaces
                    Toast.makeText(SearchActivityMain.this, "Please enter a song or artist or album", Toast.LENGTH_LONG).show();
                }else{
                    JSONObject trackData = mConnector.lookUp(searchInput, "track");
                    ArrayList<Track> tracks = JSONHelper.getTracks(trackData);
                    ListAdapterTrack trackAdapter = new ListAdapterTrack(tracks, SearchActivityMain.this);

                    mTrackView.setAdapter(trackAdapter);


                    JSONObject albumData = mConnector.lookUp(searchInput, "album");
                    ArrayList<Album> albums = JSONHelper.getAlbums(albumData);
                    ListAdapterAlbum albumAdapter = new ListAdapterAlbum(albums, SearchActivityMain.this);

                    mAlbumView.setAdapter(albumAdapter);


                    JSONObject artistData = mConnector.lookUp(searchInput, "artist");
                    ArrayList<Artist> artist = JSONHelper.getArtists(artistData);
                    ListAdapterArtist artistAdapter = new ListAdapterArtist(artist, SearchActivityMain.this);

                    mArtistView.setAdapter(artistAdapter);
                }
            }
        });
    }

    public void goToAlbumTrack(){

    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, SearchActivityMain.class);
        return i;
    }



}
