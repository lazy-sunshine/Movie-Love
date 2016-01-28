package com.example.dell.movielove;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by DeLL on 12/15/2015.
 */
public class VolleySingleton {

    public static VolleySingleton sInstance =null;
    RequestQueue mRequestQueue;

    public VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
    }
    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;

    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}





