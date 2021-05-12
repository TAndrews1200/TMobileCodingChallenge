package com.ardenai.tmproject.network

import com.ardenai.tmproject.model.listDataClasses.TMData
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("test/home")
    fun getData(): Call<TMData>
}