package com.ardenai.tmproject.view

import com.ardenai.tmproject.model.listDataClasses.TMData
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardenai.tmproject.view.adapter.MainAdapter
import com.ardenai.tmproject.viewmodel.MainViewModel
import com.ardenai.tmproject.R


class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private lateinit var recycView: RecyclerView
    lateinit var loadingSpinner: ProgressBar

    override fun onStart() {
        super.onStart()
        recycView = findViewById(R.id.main_recyc_view)
        loadingSpinner = findViewById(R.id.main_spinner)
        getRecyclerViewPopulated()
    }

    private fun getRecyclerViewPopulated() {
        mainViewModel.getTMData(::onTMDataSuccess, ::showGenericErrorToast, this)
    }

    fun onTMDataSuccess(tmData: TMData) {
        loadingSpinner.visibility = View.GONE
        //If this was actually paginated you'd want to have a way to add each new page
        //of cards here. But it's not paginated.
        recycView.layoutManager = LinearLayoutManager(this@MainActivity)
        recycView.adapter = MainAdapter(
            tmData.page.cards.toMutableList(),
            (this@MainActivity.resources.displayMetrics.widthPixels).toFloat()
        )
        recycView.visibility = View.VISIBLE
    }

    fun showGenericErrorToast(errorString: String) {
        loadingSpinner.visibility = View.GONE
        Toast.makeText(
            this@MainActivity,
            errorString,
            Toast.LENGTH_SHORT
        ).show()
    }
}
