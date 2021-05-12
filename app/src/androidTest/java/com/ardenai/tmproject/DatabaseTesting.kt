package com.ardenai.tmproject

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ardenai.tmproject.model.listDataClasses.Page
import com.ardenai.tmproject.model.listDataClasses.TMData
import com.ardenai.tmproject.model.db.Cache
import com.ardenai.tmproject.model.db.CacheDatabase
import com.ardenai.tmproject.viewmodel.MainViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTesting {

    val fakeJson = "Pretend I'm Json"
    val newModel = MainViewModel()

    @Before
    fun setup() {
        CacheDatabase.initializeDB(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun basicDBTest() {
        //To ensure the database is caching properly
        CacheDatabase.getInstance().getDao().updateCache(Cache(5, "Pretend I'm Json"))
        assertEquals(fakeJson, CacheDatabase.getInstance().getDao().readFromCache(5).jsonData)
    }

    @Test
    fun basicVMTest() {
        val fakeData = TMData(Page(listOf()))
        newModel.addToCache(fakeData)
        assertEquals(fakeData, newModel.readFromCache())
    }
}