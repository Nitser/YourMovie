package com.test.your_movie.activity

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

class HomeActivity : AppCompatActivity() {

    companion object {
        //        lateinit var userApi: UserApi
//        lateinit var boardApi: BoardApi
        val disposable = CompositeDisposable()

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

}