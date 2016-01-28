package com.example.dell.movielove;

import android.app.Application;
import android.content.Context;

/**
 * Created by DeLL on 12/15/2015.
 */
public class MyApplication extends Application {

    public static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
