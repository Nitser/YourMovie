package com.test.your_movie.activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity

import com.test.your_movie.AlbumFragment
import com.test.your_movie.LiveDataHelper
import com.test.your_movie.NetworkService
import com.test.your_movie.NoInternetFragment
import com.test.your_movie.R

class MainActivity : AppCompatActivity() {

    private var networkService: NetworkService? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        if (isNetworkConnected(application)) {
            networkService!!.loadJsonData(1)
            loadFragment(AlbumFragment.newInstance())
            swipeRefreshLayout!!.isRefreshing = true
        } else
            loadFragment(NoInternetFragment())

        LiveDataHelper.getInstance().observeMovieList().observe(this, Observer { swipeRefreshLayout!!.isRefreshing = false })

        loadFragment(AlbumFragment.newInstance())
    }

    private fun init() {
        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout!!.isEnabled = false
        networkService = NetworkService.getInstance()
    }

    fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.root, fragment)
        ft.commit()
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm != null && cm.activeNetworkInfo != null
    }
}
