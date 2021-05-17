package com.ardenai.tmproject

import androidx.multidex.MultiDexApplication
import com.ardenai.tmproject.model.db.CacheDatabase
import com.ardenai.tmproject.network.ApiManager

class TMApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        ApiManager.init()
        CacheDatabase.initializeDB(this.applicationContext)
    }
}