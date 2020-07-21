package com.test.your_movie.model

import java.io.Serializable

data class MovieModel(
        val movieId: String?,
        val movieTitle: String?,
        val movieOriginalTitle: String?,
        val voteCount: String?,
        val voteAverage: String?,
        val overview: String?,
        var releaseDate: String?,
        val popularity: String?,
        val posterPath: String?,
        val country: String?,
        val isAdult: Boolean = false,
        val originalLanguage: String?
) : Serializable
