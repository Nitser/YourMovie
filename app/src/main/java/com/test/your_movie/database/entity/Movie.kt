package com.test.your_movie.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
        @PrimaryKey @ColumnInfo(name = "id") val id: String,
        @ColumnInfo(name = "title") val title: String?,
        @ColumnInfo(name = "originalTitle") val originalTitle: String?,
        @ColumnInfo(name = "voteCount") val voteCount: String?,
        @ColumnInfo(name = "voteAverage") val voteAverage: String?,
        @ColumnInfo(name = "overview") val overview: String?,
        @ColumnInfo(name = "releaseDate") val releaseDate: String?,
        @ColumnInfo(name = "popularity") val popularity: String?,
        @ColumnInfo(name = "posterPath") val posterPath: String?,
        @ColumnInfo(name = "country") val country: String?,
        @ColumnInfo(name = "isAdult") val isAdult: String?,
        @ColumnInfo(name = "originalLanguage") val originalLanguage: String?,
        @ColumnInfo(name = "isFavorite") val isFavorite: String
)
