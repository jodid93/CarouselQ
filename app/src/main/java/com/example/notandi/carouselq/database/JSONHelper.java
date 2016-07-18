package com.example.notandi.carouselq.database;

import org.json.*;
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
