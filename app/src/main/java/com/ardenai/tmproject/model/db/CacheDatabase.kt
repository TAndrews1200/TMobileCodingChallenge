package com.ardenai.tmproject.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1, entities = [Cache::class])
abstract class CacheDatabase: RoomDatabase() {

    abstract fun getDao(): CacheDAO

    companion object{
        private lateinit var cacheDatabase: CacheDatabase
        fun initializeDB(context: Context){ //To be called when activity / application starts
            cacheDatabase = Room.databaseBuilder(
                context,
                CacheDatabase::class.java,
                "cache.db"
            //allowMainThreadQueries is not a good solution to this, and would be better replaced with
            //your favorite solution to thread management, say, Coroutines, RxJava, etc.
            ).allowMainThreadQueries().build()
        }

        fun getInstance(): CacheDatabase = cacheDatabase
    }

}