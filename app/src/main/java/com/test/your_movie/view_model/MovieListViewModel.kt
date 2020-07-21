package com.test.your_movie.view_model

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.your_movie.app.AppData
import com.test.your_movie.model.MovieModel
import com.test.your_movie.network.ApiClient
import com.test.your_movie.network.apiService.MovieApi
import com.test.your_movie.network.model.movie.Movie
import com.test.your_movie.network.model.movie.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieListViewModel : ViewModel() {
    private val movieList = MutableLiveData<ArrayList<MovieModel>>()
    private val loadPageNumber = MutableLiveData<Int>()

    private val disposable = CompositeDisposable()
    private val movieApi = ApiClient.getClient().create(MovieApi::class.java)

    fun getMovieList(): LiveData<ArrayList<MovieModel>> {
        return movieList
    }

    fun loadMovieList(releaseYear: Int, callback: MovieListCallback) {
        val page = if (loadPageNumber.value == null) {
            loadPageNumber.postValue(1)
            1
        } else
            loadPageNumber.value?.plus(1)

        disposable.add(
                movieApi.movieList(releaseYear, AppData.apiKey, page!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<MovieResponse>() {
                            override fun onSuccess(response: MovieResponse) {
                                val res = response.movieArray?.map { movieParser(it) }
                                if (movieList.value.isNullOrEmpty()) {
                                    movieList.value = ArrayList()
                                    if (!res.isNullOrEmpty())
                                        movieList.postValue(ArrayList(res))
                                } else {
                                    if (!res.isNullOrEmpty()) {
                                        val list = movieList.value
                                        list?.addAll(ArrayList(res))
                                        movieList.postValue(list)
                                    }
                                }
                                callback.onSuccess()
                            }

                            override fun onError(e: Throwable) {
                                callback.onError(NetworkErrorException(e))
                            }
                        })
        )
    }

    interface MovieListCallback {
        fun onSuccess()

        fun onError(networkError: NetworkErrorException)
    }

    fun movieParser(movieDB: Movie): MovieModel {
        with(movieDB) {
            return MovieModel(
                    movieId
                    , movieTitle
                    , movieOriginalTitle
                    , voteCount
                    , voteAverage
                    , overview
                    , releaseDate
                    , popularity
                    , posterPath
                    , country
                    , isAdult
                    , originalLanguage
            )
        }
    }

}