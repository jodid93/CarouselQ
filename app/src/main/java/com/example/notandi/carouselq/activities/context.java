package com.example.notandi.carouselq.activities;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Notandi on 15.6.2016.
 */
public class context extends Activity {

    private static context instance = null;

    private context() {       }

    public static context getInstance() {
        if (instance == null) {
            instance = new context ();
        }
        return instance;
    }

    private Context contextInstance;
    public void init(Context context){
        this.contextInstance = context.getApplicationContext();
    }

    public Context getContext(){
        return this.contextInstance;
    }
}
