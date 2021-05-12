package com.ardenai.tmproject

import android.app.Application
import com.ardenai.tmproject.model.db.CacheDatabase
import com.ardenai.tmproject.network.ApiManager

class TMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiManager.init()
        CacheDatabase.initializeDB(this.applicationContext)
    }
}