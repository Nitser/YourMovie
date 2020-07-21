package com.test.your_movie.network.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Movie : Serializable {
    @SerializedName("id")
    @Expose
    lateinit var movieId: String

    @SerializedName("title")
    @Expose
    val movieTitle: String? = null

    @SerializedName("original_title")
    @Expose
    val movieOriginalTitle: String? = null

    @SerializedName("vote_count")
    @Expose
    val voteCount: String? = null

    @SerializedName("vote_average")
    @Expose
    val voteAverage: String? = null

    @SerializedName("overview")
    @Expose
    val overview: String? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    @SerializedName("popularity")
    @Expose
    val popularity: String? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null

    @SerializedName("country")
    @Expose
    val country: String? = null

    @SerializedName("adult")
    @Expose
    val isAdult: Boolean = false

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null
}
