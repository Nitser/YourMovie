package com.test.your_movie.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.test.your_movie.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
//        setA(findViewById(R.id.app_bar))
        loadUserFromStorage()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    private fun loadUserFromStorage() {
//        val getUser = InternalStorageService(this, object : InternalStorageService.PostExecuteCallback {
//            override fun doAfter() {
//                if (InternalStorageService.entryStatus) {
//                    Handler().postDelayed({
//                        Const.token = InternalStorageService.user.token
//                        val intent = Intent(applicationContext, HomeActivity::class.java)
//                        intent.putExtra("user", InternalStorageService.user)
//                        startActivity(intent)
//                        finish()
//                    }, SPLASH_DISPLAY_LENGTH.toLong())
//                } else {
//                    Handler().postDelayed({
//                        findNavController(R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_mainFragment)
//                    }, SPLASH_DISPLAY_LENGTH.toLong())
//                }
//            }
//        })
//        getUser.setIGetInternalData(GetUserIdAndToken())
//        getUser.execute()
    }

    private fun saveUserOnStorage() {
//        val internalStorageService = InternalStorageService(this, null)
//        with(mainUserViewModel.getMainUser().value!!) {
//            internalStorageService.setUserData(id, token, password)
//        }
//        internalStorageService.setISetInternalData(SetUserIdAndToken())
//        internalStorageService.execute()
    }
}