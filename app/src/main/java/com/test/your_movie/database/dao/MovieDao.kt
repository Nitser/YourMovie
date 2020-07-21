package com.test.your_movie.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.test.your_movie.database.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE id IN (:movieId)")
    fun loadById(movieId: Int): Movie

    @Insert
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

}