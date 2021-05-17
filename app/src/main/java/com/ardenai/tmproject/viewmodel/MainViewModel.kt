package com.ardenai.tmproject.viewmodel

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.ardenai.tmproject.R
import com.ardenai.tmproject.model.db.Cache
import com.ardenai.tmproject.model.db.CacheDatabase.Companion.getInstance
import com.ardenai.tmproject.model.listDataClasses.TMData
import com.ardenai.tmproject.network.ApiManager
import com.ardenai.tmproject.network.NetworkUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class MainViewModel : ViewModel() {
    private val gson = Gson()

    val compositeDisposable = CompositeDisposable()

    fun getTMData(
        onSuccess: (TMData) -> Unit,
        displayToast: (String) -> Unit,
        context: Context
    ) {
        if (NetworkUtils.hasValidNetwork(context)) {
            compositeDisposable.add(
                ApiManager.api.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { tmData ->
                        addToCache(tmData)
                        onSuccess(tmData)
                    },
                    onError = {error ->
                        readFromCache()?.let {
                            displayToast(
                                context.getString(
                                    R.string.get_data_failed_with_cache,
                                    error.message
                                )
                            )
                            onSuccess(it)
                        } ?: displayToast(
                            context.getString(
                                    R.string.get_data_failed,
                                    error.message
                            )
                        )
                    }
                ))
        } else {
            readFromCache()?.let() {
                displayToast(context.getString(R.string.get_data_error_no_network_with_cache))
                onSuccess(it)
            } ?: displayToast(context.getString(R.string.get_data_error_no_network))
        }
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        val CACHE_ID = 1
    }

}