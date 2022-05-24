package dev.ogabek.kotlin.network

interface VolleyHandler {
    fun onSuccess(respond: String)
    fun onError(error: String)
}