package com.example.notandi.carouselq.database;

import android.support.annotation.NonNull;

import com.example.notandi.carouselq.queueController.Track;

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
                //JSONArray artists = items.getJSONArray("artist");
                //JSONObject artist = list.getJSONObject(0);

                tracks.add(

                        new Track(
                                item.getString("uri"),
                                item.getString("name"),
                                item.getInt("duration_ms"),
                                /*artist.getString("name")*/"Muse"
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
