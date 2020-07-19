package com.test.your_movie.app

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object AppData {

    //        lateinit var boardApi: MovieApi
    const val MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    const val MOVIE_LIST_URL = "https://api.themoviedb.org"
    const val apiKey = "6306465c1312b9eb83336efbe2a81f54"

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(activity: Activity, editTextView: View) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextView, InputMethodManager.SHOW_IMPLICIT)
    }

    fun exit(activity: Activity) {
//            val clearUser = InternalStorageService(activity, object : InternalStorageService.PostExecuteCallback {
//                override fun doAfter() {
//                    Const.isEntry = false
//                    val intent = Intent(activity, EntryActivity::class.java)
//                    activity.startActivity(intent)
//                    activity.finish()
//                }
//            })
//            clearUser.setISetInternalData(ClearUserIdAndToken())
//            clearUser.execute()
    }
}