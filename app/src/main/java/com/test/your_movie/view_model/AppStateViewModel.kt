package com.test.your_movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {
    private val qrCodeStart = MutableLiveData<Boolean>()

    fun getQRCodeState(): LiveData<Boolean> {
        return qrCodeStart
    }

    fun setQRCodeState(newState: Boolean) {
        qrCodeStart.postValue(newState)
    }

}