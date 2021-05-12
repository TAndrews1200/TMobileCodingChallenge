package com.ardenai.tmproject.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheDAO {

    @Query("SELECT * FROM CACHE WHERE id = :id" )
    fun readFromCache(id: Int): Cache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCache(cache: Cache)

}