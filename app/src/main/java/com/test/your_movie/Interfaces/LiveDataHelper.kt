package com.test.your_movie.Interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.test.your_movie.network.model.movie.Movie

class LiveDataHelper {
    private val movieList = MediatorLiveData<List<Movie>>()

    fun updateMovieList(newMoviewList: List<Movie>) {
        movieList.postValue(newMoviewList)
    }

    fun observeMovieList(): LiveData<List<Movie>> {
        return movieList
    }

//    companion object {
//        private var liveDataHelper: LiveDataHelper? = null
//
//        val instance: LiveDataHelper
//            @Synchronized get() {
//                if (liveDataHelper == null)
//                    liveDataHelper = LiveDataHelper()
//                return liveDataHelper
//            }
//    }

}