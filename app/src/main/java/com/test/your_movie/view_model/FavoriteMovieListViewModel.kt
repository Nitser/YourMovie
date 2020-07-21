package com.test.your_movie.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.your_movie.database.AppDatabase
import com.test.your_movie.database.entity.Movie
import com.test.your_movie.database.repository.MovieRepository
import com.test.your_movie.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMovieListViewModel : ViewModel() {
    private lateinit var repository: MovieRepository
    private val favoriteMovieList = MutableLiveData<ArrayList<MovieModel>>()
    private val isFavoriteMovie = MutableLiveData<Boolean>()

    fun init(context: Context) {
        val movieDao = AppDatabase.getDatabase(context).movieDao()
        repository = MovieRepository(movieDao)
    }

    fun getFavoriteMovieList(): LiveData<ArrayList<MovieModel>> {
        return favoriteMovieList
    }

    fun getIsFavoriteMovie(): LiveData<Boolean> {
        return isFavoriteMovie
    }

    fun throwOffIsFavoriteMovie() {
        isFavoriteMovie.postValue(false)
    }

    fun loadFavoriteMovieList() = viewModelScope.launch(Dispatchers.IO) {
        val movies = repository.getAll()
        favoriteMovieList.postValue(ArrayList(movies.map { movieParser(it) }))
    }

    fun saveFavoriteMovie(movie: MovieModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movieParserToBD(movie))
        val updateList = favoriteMovieList.value
        updateList?.add(movie)
        favoriteMovieList.postValue(updateList)
    }

    fun deleteFavoriteMovie(movie: MovieModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(movieParserToBD(movie))
        val updateList = favoriteMovieList.value
        updateList?.remove(movie)
        favoriteMovieList.postValue(favoriteMovieList.value)
    }

    fun checkMovieIsSaved(movie: MovieModel) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val resMovie = repository.loadById(movie.movieId.toInt())
            Log.i("RESSSSS", resMovie.toString())
            isFavoriteMovie.postValue(resMovie.isFavorite.toBoolean())
        } catch (ex: Exception) {
        }
    }

    private fun movieParser(movieDB: Movie): MovieModel {
        with(movieDB) {
            return MovieModel(
                    id
                    , title
                    , originalTitle
                    , voteCount
                    , voteAverage
                    , overview
                    , releaseDate
                    , popularity
                    , posterPath
                    , country
                    , isAdult?.toBoolean() ?: false
                    , originalLanguage
                    , isFavorite.toBoolean()
            )
        }
    }

    private fun movieParserToBD(movie: MovieModel): Movie {
        with(movie) {
            return Movie(
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
                    , isAdult.toString()
                    , originalLanguage
                    , isFavorite.toString()
            )
        }
    }

}