package com.test.your_movie.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.your_movie.R

class HomeActivity : AppCompatActivity() {

    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        navigationView = findViewById(R.id.bottom_navigation_home)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationView.setOnNavigationItemReselectedListener(mOnNavigationItemReSelectedListener)
        navigationView.visibility = View.GONE
    }

    fun setBottomNavigationViewVisible() {
        navigationView.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_bottom_movie_list -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.movieListFragment)
                item.isChecked = true
            }
            R.id.menu_bottom_favorite_movie_list -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.favoriteMovieListFragment)
                item.isChecked = true
            }
        }
        false
    }

    private val mOnNavigationItemReSelectedListener = BottomNavigationView.OnNavigationItemReselectedListener {
        supportFragmentManager.popBackStackImmediate()
    }

}