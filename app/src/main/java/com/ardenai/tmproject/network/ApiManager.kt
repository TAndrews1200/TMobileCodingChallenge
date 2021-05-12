package com.ardenai.tmproject.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiManager {

    lateinit var api : Api

    fun init() {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(LoggingInterceptor())
        val client = clientBuilder.build()
        //the baseUrl should really be stored somewhere better so it's easier to update, in some
        //constants file somewhere
        api = Retrofit.Builder()
            .baseUrl("https://private-8ce77c-tmobiletest.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(Api::class.java)
    }
}
internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //Normally we'd do something here, but this is a pretty simple application and we don't
        //really have any particular needs here we can't handle elsewhere
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        return response
    }
}