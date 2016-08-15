package com.example.notandi.carouselq.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.notandi.carouselq.activities.context;
import com.example.notandi.carouselq.queueController.Track;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/*
* for starting my local db run
* C:\Program Files\PostgreSQL\9.5\bin>pg_ctl start -D "C:\Program Files\PostgreSQL\9.5\data"
* with cmd Admin
* */

public class DBConnector{

    private Context appContext;
    private String currentMethod;
    private static String currentUrl;
    private static String mMessage;
    private static DBConnector instance = null;
    private static int BugCounter = 0;

    protected DBConnector() {
        context wow = context.getInstance();
        this.appContext = wow.getContext();
    }

    public static DBConnector getInstance() {
        if(instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////
    //
    //  NETWORK FUNCTIONS
    //
    //////////////////////////////////////////////////////////////


    public void testConnection() {
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.testConnection();
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    public JSONArray lookUp(String Search){
        JSONArray data = new JSONArray();
        data.put(lookUp(Search, "track"));
        data.put(lookUp(Search, "album"));
        data.put(lookUp(Search, "artist"));

        return data;
    }

    public JSONObject lookUp(String Search, String type){
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.getSongs(Search, type);
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            try {
                debugg("bid eftir svari fra async method");
                String message = task.execute().get();
                debugg("svar komid");
                return JSONHelper.getJSONObject(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject getAlbumTracks(String url){
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = url;
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            try {
                debugg("bid eftir svari fra async method");
                String message = task.execute().get();
                debugg("svar komid");
                return JSONHelper.getJSONObject(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Track> getQueue(String queueID){
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.getQueue(queueID);
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            try {
                debugg("bid eftir svari fra async method");
                String message = task.execute().get();
                debugg("svar komid");
                return JSONHelper.getQueueFromJSON(JSONHelper.getJSONArray(message));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addSongToQueue(String hashName, String trackUri,String trackName,String trackBand,int trackDur){
        if(checkNetwork()){
            this.currentMethod = "SUMBMIT";
            currentUrl = urls.addSongToQueue(hashName, trackUri, trackName.replaceAll(" ", "+"), trackBand.replaceAll(" ", "+"), trackDur);
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    public void registerUser(String userName, String hashedUserName, String queueId, boolean owner){
        if(checkNetwork()){
            this.currentMethod = "SUMBMIT";
            currentUrl = urls.registerNewUser(userName, hashedUserName, queueId, owner);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    public void removeSong(String hashUserName, String trackUri){
        if(checkNetwork()){
            this.currentMethod = "SUMBMIT";
            currentUrl = urls.removeSong(hashUserName, trackUri);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    public void initializeDB(){
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.initializeDB();
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    public JSONObject testSpotify(){
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.testSpotify();
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            try {
                debugg("bid eftir svari fra async method");
                String message = task.execute().get();

                return JSONHelper.spotifyTest(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean doesQueueExist(String queueId){
        boolean doesExist = false;
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.doesQueueExist(queueId);
            AsyncTask<Void, Void, String> task = new FetchDataTask();
            try {
                debugg("bid eftir svari fra async method");
                String message = task.execute().get();

                doesExist = JSONHelper.doesQueueExist(message);
                debugg(Boolean.toString(doesExist));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        debugg("svar komid i hus og er");
        debugg(mMessage);
        return doesExist;
    }

    public void makeUserInactive(String hashedUserName) {
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.makeUserInactive(hashedUserName);
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    ///////////////////////////////////////////////////////////////
    //
    //  END OF NETWORK FUNCTIONS
    //
    //////////////////////////////////////////////////////////////

    private boolean checkNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager) this.appContext.getSystemService(this.appContext.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }



    private class FetchDataTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            String message = new DBConnector().fetchData();
            return message;
        }

        @Override
        protected void onPostExecute(String message) {
            //debugg("async svaradi post execute: \n"+message);
            mMessage = message ;
        }
    }

    public String fetchData() {
        debugg("fetching data");
        try {
            String url = Uri.parse(currentUrl)
                    .buildUpon()
                    .build().toString();
            debugg(url);
            String jsonString = getUrlString(url);
            mMessage = jsonString;
            return jsonString;
        } catch (IOException ioe) {
            debugg(ioe.toString());
        }
        debugg("returning null");
        return null;
    }

    public String getUrlString(String urlSpec) throws IOException {
        try{
            return new String(getUrlBytes(urlSpec));
        }catch(Exception e){
            debugg(e.getMessage());
            return "";
        }
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        debugg("nadi ad komast  ad conn");
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            debugg("getting input Stream");
            InputStream in = conn.getInputStream();
            debugg("finished getting input stream");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[320000];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }catch (IOException e){
            debugg(e.toString());
        }finally {
            conn.disconnect();
        }
        return null;
    }

    private static void debugg(String error){
        System.out.println("---------------"+error+"----------------"+BugCounter);
        BugCounter++;
    }

}
