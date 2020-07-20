package com.test.your_movie.view_model

import androidx.lifecycle.ViewModel

class InitializationViewModel : ViewModel() {

    fun saveUserData(login: String?, password: String?, callback: InitializationCallback) {
        if (!login.isNullOrEmpty() && !password.isNullOrEmpty()) {
            callback.onSuccess()
        } else {
            callback.onError()
        }
    }

    interface InitializationCallback {
        fun onSuccess()

        fun onError()
    }

}