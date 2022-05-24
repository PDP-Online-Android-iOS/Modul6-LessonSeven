package dev.ogabek.java;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {

    public static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    RequestQueue requestQueue = null;

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            return Volley.newRequestQueue(getApplicationContext());
        }
        else  {
            return requestQueue;
        }
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
