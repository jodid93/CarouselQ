package com.example.notandi.carouselq;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Notandi on 15.6.2016.
 */
public class context extends Activity {

    private static context instance = null;
    protected context() {
        // Exists only to defeat instantiation.
    }
    public static context getInstance() {
        if(instance == null) {
            instance = new context();
        }
        return instance;
    }

    public Context getContext(){
        return this.getApplicationContext();
    }
}
