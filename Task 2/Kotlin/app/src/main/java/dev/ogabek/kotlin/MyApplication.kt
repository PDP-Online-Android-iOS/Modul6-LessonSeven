package dev.ogabek.kotlin

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val requestQueue: RequestQueue? = null
        get() {
            return field ?: Volley.newRequestQueue(applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>?) {
        requestQueue!!.add(request)
    }

    companion object {
        var instance: MyApplication? = null
    }
}