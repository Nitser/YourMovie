package com.test.your_movie.network.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.test.your_movie.network.model.movie.Movie

/**
 * Receive object from URL
 */
class MovieResponse {
    @SerializedName("results")
    @Expose
    val movieArray: Array<Movie>? = null
}
