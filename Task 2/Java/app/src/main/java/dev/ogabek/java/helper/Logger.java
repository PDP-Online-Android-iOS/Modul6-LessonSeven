package dev.ogabek.java.helper;

import android.util.Log;

import dev.ogabek.java.network.VolleyHttp;

public class Logger {

    public static void d(String tag, String msg) {
        if (VolleyHttp.IS_TESTER) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (VolleyHttp.IS_TESTER) Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (VolleyHttp.IS_TESTER) Log.v(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (VolleyHttp.IS_TESTER) Log.e(tag, msg);
    }

}
