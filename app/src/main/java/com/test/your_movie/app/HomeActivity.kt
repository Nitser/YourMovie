package com.test.your_movie.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
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
        navigationView.setOnNavigationItemReselectedListener(mOnNavigationItemReSelectedListener)
        navigationView.setupWithNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
        navigationView.setOnNavigationItemSelectedListener { item ->
            onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment))
        }
        navigationView.visibility = View.GONE
    }

    fun setBottomNavigationViewVisible(isVisible: Boolean) {
        if (isVisible) {
            navigationView.visibility = View.VISIBLE
        } else {
            navigationView.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    private val mOnNavigationItemReSelectedListener = BottomNavigationView.OnNavigationItemReselectedListener {
        supportFragmentManager.popBackStackImmediate()
    }

}