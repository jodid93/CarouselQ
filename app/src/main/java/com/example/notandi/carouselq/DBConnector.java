package com.example.notandi.carouselq;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public void testConnection() {
        if(checkNetwork()){
            this.currentMethod = "GET";
            currentUrl = urls.testConnection();
            debugg(currentUrl);
            AsyncTask<Void,Void,String> task = new FetchDataTask();
            task.execute();
        }
    }

    public void registerUser(String userName, String hashedUserName, String queueId){
        if(checkNetwork()){
            this.currentMethod = "SUMBMIT";
            currentUrl = urls.registerNewUser(userName, hashedUserName, queueId);
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
            debugg(message);
            return message;
        }

        @Override
        protected void onPostExecute(String message) {
            mMessage = message;
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
            debugg(jsonString);
            return jsonString;
        } catch (IOException ioe) {
            debugg(ioe.toString());
        }
        debugg("returning null");
        return null;
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
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
            byte[] buffer = new byte[1024];
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
