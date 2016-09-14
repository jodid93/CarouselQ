package com.example.notandi.carouselq.database;

import android.support.annotation.NonNull;

import com.example.notandi.carouselq.queueController.Album;
import com.example.notandi.carouselq.queueController.Artist;
import com.example.notandi.carouselq.queueController.Track;
import com.example.notandi.carouselq.users.UserInfo;

import org.json.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by Jósúa on 18-Jul-16.
 */
public class JSONHelper {

    public static boolean doesQueueExist(String jsonResponse){
        try {
            JSONObject res = new JSONObject(jsonResponse);
            String status = res.getString("status");
            debugg("status inn er: "+status);
            if(status.equals("true")){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void debugg(String error){
        System.out.println("---------------"+error+"----------------");
    }

    public static JSONObject spotifyTest(String message) {
        try {
            JSONObject res = new JSONObject(message);
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(String message){
        try {
            JSONObject res = new JSONObject(message);
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJSONArray(String message){
        try {
            JSONArray res = new JSONArray(message);
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Track> getTracks(JSONObject res) {
        debugg("eg");
        debugg("er");
        debugg("ad");
        debugg("extracta");
        debugg("json");

        ArrayList<Track> tracks = new ArrayList<Track>();

        try {
            JSONObject items = res.getJSONObject("tracks");
            JSONArray list = items.getJSONArray("items");
            for(int i = 0; i < list.length(); i++){
                JSONObject item = list.getJSONObject(i);
                debugg(item.getString("name"));
                JSONArray artists = item.getJSONArray("artists");
                JSONObject artist = artists.getJSONObject(0);

                tracks.add(

                        new Track(
                                item.getString("uri"),
                                item.getString("name"),
                                item.getInt("duration_ms"),
                                artist.getString("name"),
                                i,
                                UserInfo.getInstance().getUserName(),
                                0,
                                UserInfo.getInstance().getHashedUserName()
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tracks;//tracks.toArray(new String[0]);
    }


    public static ArrayList<Track> getTracksFromAlbum(JSONObject res) {
        debugg("eg");
        debugg("er");
        debugg("ad");
        debugg("extracta");
        debugg("json");

        ArrayList<Track> tracks = new ArrayList<Track>();

        try {
            JSONArray list = res.getJSONArray("items");
            for(int i = 0; i < list.length(); i++){
                JSONObject item = list.getJSONObject(i);
                debugg(item.getString("name"));
                JSONArray artists = item.getJSONArray("artists");
                JSONObject artist = artists.getJSONObject(0);

                tracks.add(

                        new Track(
                                item.getString("uri"),
                                item.getString("name"),
                                item.getInt("duration_ms"),
                                artist.getString("name"),
                                i,
                                UserInfo.getInstance().getUserName(),
                                0,
                                UserInfo.getInstance().getHashedUserName()
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tracks;//tracks.toArray(new String[0]);
    }

    public static ArrayList<Artist> getArtists(JSONObject res) {
        debugg("eg");
        debugg("er");
        debugg("ad");
        debugg("extracta");
        debugg("json");

        ArrayList<Artist> artists = new ArrayList<Artist>();

        try {
            JSONObject items = res.getJSONObject("artists");
            JSONArray list = items.getJSONArray("items");
            for(int i = 0; i < list.length(); i++){
                JSONObject item = list.getJSONObject(i);
                debugg(item.getString("name"));

                artists.add(

                        new Artist(
                                item.getString("href")+"/albums",
                                item.getString("name"),
                                i
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return artists;//tracks.toArray(new String[0]);
    }

    public static ArrayList<Album> getAlbums(JSONObject res) {
        debugg("eg");
        debugg("er");
        debugg("ad");
        debugg("extracta");
        debugg("json");

        ArrayList<Album> albums = new ArrayList<Album>();

        try {
            JSONObject items = res.getJSONObject("albums");
            JSONArray list = items.getJSONArray("items");
            for(int i = 0; i < list.length(); i++){
                JSONObject item = list.getJSONObject(i);
                debugg(item.getString("name"));
                //JSONArray artists = item.getJSONArray("artists");
                //JSONObject artist = artists.getJSONObject(0);

                albums.add(

                        new Album(
                                item.getString("href"),
                                item.getString("name"),
                                i
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albums;//tracks.toArray(new String[0]);
    }


    public static ArrayList<Album> getAlbumsFromArtist(JSONObject res) {
        debugg("eg");
        debugg("er");
        debugg("ad");
        debugg("extracta");
        debugg("json");

        ArrayList<Album> albums = new ArrayList<Album>();

        try {
            JSONArray list = res.getJSONArray("items");
            for(int i = 0; i < list.length(); i++){
                JSONObject item = list.getJSONObject(i);
                debugg(item.getString("name"));
                //JSONArray artists = item.getJSONArray("artists");
                //JSONObject artist = artists.getJSONObject(0);

                albums.add(

                        new Album(
                                item.getString("href"),
                                item.getString("name"),
                                i
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albums;//tracks.toArray(new String[0]);
    }

    public static ArrayList<Track> getQueueFromJSON(JSONArray res) {
        debugg("eg");
        debugg("er");
        debugg("ad");
        debugg("extracta");
        debugg("json");

        ArrayList<Track> tracks = new ArrayList<Track>();

        try {

            for(int i = 0; i < res.length(); i++){
                JSONObject item = res.getJSONObject(i);
                tracks.add(
                        new Track(
                                item.getString("songUri"),
                                item.getString("songName"),
                                item.getInt("songDuration"),
                                item.getString("songBand"),
                                i,
                                item.getString("userName"),
                                item.getInt("songSkips"),
                                item.getString("userHash")
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tracks;//tracks.toArray(new String[0]);
    }


    /*JSONObject obj = new JSONObject(str);
    String n = obj.getString("name");
    int a = obj.getInt("age");
    System.out.println(n + " " + a);  // prints "Alice 20"

    JSONObject obj = new JSONObject(" .... ");
    String pageName = obj.getJSONObject("pageInfo").getString("pageName");

    JSONArray arr = obj.getJSONArray("posts");
    for (int i = 0; i < arr.length(); i++)
    {
        String post_id = arr.getJSONObject(i).getString("post_id");
        ......
    }*/

}
