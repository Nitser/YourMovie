package com.test.your_movie.database.repository

import com.test.your_movie.database.dao.MovieDao
import com.test.your_movie.database.entity.Movie

class MovieRepository(private val movieDao: MovieDao) {

    fun getAll(): List<Movie> {
        return movieDao.getAll()
    }

    fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    fun delete(movie: Movie) {
        movieDao.delete(movie)
    }

    fun loadById(movieId: Int): Movie {
        return movieDao.loadById(movieId)
    }

}