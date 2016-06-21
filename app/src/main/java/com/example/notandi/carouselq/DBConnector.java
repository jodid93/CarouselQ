package com.example.notandi.carouselq;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
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
    private static DBConnector instance = null;
    private static String mMessage;

    /*public DBConnector(){
        this.appContext = new context();
    }*/

    protected DBConnector() {
        context wow = context.getInstance();
        this.appContext = wow.getContext();
        System.out.println("ekkert vesen her");
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
            AsyncTask<Void,Void,String> task = new FetchItemsTask();
            task.execute();
            System.out.println("#######FUCKING SHIT THAD VIRKAR ---- " + mMessage);
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            //return new DBConnector().fetchItems();
            String message = new DBConnector().fetchItems();
            System.out.println("#######FUCKING SHIT THAD VIRKAR 2222 ---- " + message);
            return message;
        }

        @Override
        protected void onPostExecute(String message) {
            mMessage = message;
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

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        debugg(url.toString(), 666);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        try {
            debugg("herna", 1);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            debugg("herna", 2);
            InputStream in = conn.getInputStream();
            debugg("herna", 3);
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                debugg("herna", 4);
                throw new IOException(conn.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            debugg("herna", 5);
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            debugg("herna", 6);
            out.close();
            return out.toByteArray();
        } finally { conn.disconnect(); }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String fetchItems() {
        try {
            System.out.println("ER AD REYNA AD BUA TIL URL");
            String url = Uri.parse(urls.testConnection())
                    .buildUpon()
                    .build().toString();
            System.out.println("HERNA ER STRENGURINN URL ---- "+url);
            String jsonString = getUrlString(url);
            System.out.println("##########" + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            return jsonString;
        } catch (IOException ioe) {
            debugg(ioe.toString(),55);
        } catch (JSONException je) {
            debugg(je.toString(),78);
        }
        return "jsonString";
    }








    private static void debugg(String error, int num){
        System.out.println("---------------"+error+"----------------"+num);
    }
















 //   private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
 //       @Override
 //       protected String doInBackground(String... urls) {

 //           // params comes from the execute() call: params[0] is the url.
 //           try {
 //               return downloadUrl(urls[0]);
 //           } catch (IOException e) {
 //               return "Unable to retrieve web page. URL may be invalid.";
 //           }
 //       }

 //       // onPostExecute displays the results of the AsyncTask.
 //       @Override
 //       protected void onPostExecute(String result) {
 //           System.out.println("vid fengum ut thennan streng:"+result);
 //       }
 //   }



 // private String downloadUrl(String myurl) throws IOException {
 //     InputStream is = null;
 //     // Only display the first 500 characters of the retrieved
 //     // web page content.
 //     int len = 500;

 //     try {
 //         URL url = new URL(myurl);
 //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();

 //         //conn.setReadTimeout(10000 /* milliseconds */);
 //         //conn.setConnectTimeout(15000 /* milliseconds */);
 //         conn.setRequestMethod("GET");
 //         conn.setRequestProperty("Accept", "application/json");
 //         conn.setDoInput(true);
 //         // Starts the query
 //         System.out.println("URL ID ER HERNA -----"+conn.getURL());
 //         conn.connect();
 //         System.out.println("#################NAER HINGAD###############");
 //         if (conn.getResponseCode() != 200) {
 //             System.out.println("#################KASTAR EXC###############RESPONSE   "+conn.getResponseMessage());
 //             throw new RuntimeException("Failed : HTTP error code : "
 //                     + conn.getResponseCode());
 //         }else{
 //             System.out.println("#################EFTIR###############RESPONSE   "+conn.getResponseMessage());
 //         }
 //         //Log.d(DEBUG_TAG, "The response is: " + response);
 //         //System.out.println("tenging nadist response is-------------------------------------------------:"+response);
 //         System.out.println("#################EFTIREFTIR###############   "+conn.getResponseCode());
 //         is = conn.getInputStream();
 //         System.out.println("#################EITTHVAD###########");
 //         // Convert the InputStream into a string
 //         String contentAsString = readIt(is, len);
 //         System.out.println("#################EITTHVAD22222###########"+contentAsString);
 //         return contentAsString;

 //         // Makes sure that the InputStream is closed after the app is
 //         // finished using it.
 //     }
 //     finally
 //     {
 //         if (is != null) {
 //             is.close();
 //         }
 //     }
 // }

 // public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
 //     Reader reader = null;
 //     reader = new InputStreamReader(stream, "UTF-8");
 //     char[] buffer = new char[len];
 //     reader.read(buffer);
 //     return new String(buffer);
 // }
 //
}
