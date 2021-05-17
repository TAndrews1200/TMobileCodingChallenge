package com.ardenai.tmproject.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat

object NetworkUtils {
    fun hasValidNetwork (
        context: Context
    ) : Boolean {
        val conMan = ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            (conMan?.activeNetwork != null)
        } else {
            //While this is deprecated, it's only deprecated a few versions above M
            (conMan?.activeNetworkInfo != null)
        }
    }
}