package com.ardenai.tmproject.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.ardenai.tmproject.model.listDataClasses.Page
import com.ardenai.tmproject.model.listDataClasses.TMData
import com.ardenai.tmproject.model.db.Cache
import com.ardenai.tmproject.model.db.CacheDatabase.Companion.getInstance
import com.ardenai.tmproject.network.ApiManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val gson = Gson()
    fun getTMData(
        onSuccess: (TMData) -> Unit,
        onError: () -> Unit
    ) {
        ApiManager.api.getData().enqueue(object : Callback<TMData> {

            override fun onFailure(call: Call<TMData>, t: Throwable) {
                readFromCache()?.let {
                    onSuccess(it)
                }?: onError()
            }

            override fun onResponse(call: Call<TMData>, response: Response<TMData>) {
                if (response.isSuccessful) {
                    val tmData = response.body() ?: TMData(Page(listOf()))
                    addToCache(tmData)
                    onSuccess(tmData)

                } else {
                    readFromCache()?.let {
                        onSuccess(it)
                    }?: onError()
                }
            }


        })
    }

    @VisibleForTesting
    fun addToCache(tmData: TMData) {
        val json = gson.toJson(tmData)
        getInstance().getDao().updateCache(Cache(CACHE_ID, json))
    }

    @VisibleForTesting
    fun readFromCache(): TMData? {
        val cache: Cache? = getInstance().getDao().readFromCache(CACHE_ID)
        return gson.fromJson(cache?.jsonData, TMData::class.java)
    }

    companion object{
        val CACHE_ID = 1
    }

}