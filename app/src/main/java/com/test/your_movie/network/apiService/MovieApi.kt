package com.test.your_movie.network.apiService

import com.test.your_movie.network.model.movie.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface MovieApi {

    //    3/discover/movie?primary_release_year=2019&sort_by=popularity.desc&api_key=6306465c1312b9eb83336efbe2a81f54
    @GET("3/discover/movie?sort_by=popularity.desc")
    fun movieList(@Query("primary_release_year") releaseYear: Int, @Query("api_key") apiKey: String
                  , @Query("page") pageNumber: Int): Single<MovieResponse>

}
