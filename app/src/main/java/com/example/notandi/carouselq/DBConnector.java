package com.example.notandi.carouselq;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBConnector{

    private Context appContext;
    private String currentMethod;
    private static DBConnector instance = null;

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
            new DownloadWebpageTask().execute(urls.testConnection());
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

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            System.out.println("vid fengum ut thennan streng:"+result);
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //conn.setReadTimeout(10000 /* milliseconds */);
            //conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            // Starts the query
            System.out.println("URL ID ER HERNA -----"+conn.getURL());
            conn.connect();
            if (conn.getResponseCode() != 200) {
                System.out.println("#################KASTAR EXC###############RESPONSE   "+conn.getResponseMessage());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }else{
                System.out.println("#################EFTIR###############RESPONSE   "+conn.getResponseMessage());
            }
            //Log.d(DEBUG_TAG, "The response is: " + response);
            //System.out.println("tenging nadist response is-------------------------------------------------:"+response);
            System.out.println("#################EFTIREFTIR###############   "+conn.getResponseCode());
            is = conn.getInputStream();
            System.out.println("#################EITTHVAD###########");
            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            System.out.println("#################EITTHVAD22222###########"+contentAsString);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }
        finally
        {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
