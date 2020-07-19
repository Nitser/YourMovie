package com.test.your_movie.Interfaces

import com.test.your_movie.network.model.movie.Movie

import java.util.ArrayList

class NetworkService private constructor() {
//    private val mRetrofit: Retrofit
//    private val liveDataHelper: LiveDataHelper
    private val movieList = ArrayList<Movie>()

//    val jsonApi: JSONPlaceHolderApi
//        get() = mRetrofit.create(JSONPlaceHolderApi::class.java)

    init {
//        mRetrofit = Retrofit.Builder().baseUrl(MOVIE_LIST_URL).addConverterFactory(GsonConverterFactory.create()).build()
//        liveDataHelper = LiveDataHelper.Companion.getInstance()
    }

    fun loadJsonData(pageNumber: Int) {
        val TAG = "LoadJSONData"

//        NetworkService.instance
//                .jsonApi
//                .movieList(pageNumber)
//                .enqueue(object : Callback<MovieResponse> {
//                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                        val jsonMovieResponse = response.body()
//
//                        if (jsonMovieResponse != null) {
//                            val newList = ArrayList(Arrays.asList(*jsonMovieResponse.movieArray!!))
//
//                            for (m in newList) {
//                                val from = SimpleDateFormat("yyyy-mm-dd", Locale.UK)
//                                val to = SimpleDateFormat("d MMMM yyyy", Locale.UK)
//                                var date: Date? = null
//                                try {
//                                    date = from.parse(m.releaseDate!!)
//                                } catch (e: ParseException) {
//                                    e.printStackTrace()
//                                }
//
//                                m.releaseDate = to.format(date!!)
//                            }
//
//                            movieList.addAll(newList)
////                            liveDataHelper.updateMovieList(movieList)
//
//                        } else {
//                            Log.i(TAG, "null objects $response")
//                        }
//                    }
//
//                    /**
//                     * Unsuccessful connection calls the NoInternetFragment
//                     */
//                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                        Log.i("JsonCall", "Error occurred while getting request!")
//                        //                        loadNoInternetFragment();
//                        t.printStackTrace()
//                    }
//                })
    }

//    companion object {
        private var mInstance: NetworkService? = null
        private val BASE_URL = "https://api.themoviedb.org"
//
//        val instance: NetworkService
//            get() {
//                if (mInstance == null) {
//                    mInstance = NetworkService()
//                }
//                return mInstance
//            }
//    }
}
