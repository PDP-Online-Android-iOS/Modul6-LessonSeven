package dev.ogabek.kotlin.helper

import android.util.Log
import dev.ogabek.kotlin.network.VolleyHttp

object Logger {
    fun d(tag: String?, msg: String?) {
        if (VolleyHttp.IS_TESTER) if (msg != null) Log.d(tag, msg)
    }

    fun i(tag: String?, msg: String?) {
        if (VolleyHttp.IS_TESTER) if (msg != null) Log.i(tag, msg)
    }

    fun v(tag: String?, msg: String?) {
        if (VolleyHttp.IS_TESTER) if (msg != null) Log.v(tag, msg)
    }

    fun e(tag: String?, msg: String?) {
        if (VolleyHttp.IS_TESTER) if (msg != null) Log.e(tag, msg)
    }
}
