package com.ardenai.tmproject.network

import com.ardenai.tmproject.model.listDataClasses.TMData
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("test/home")
    fun getData(): Single<TMData>
}