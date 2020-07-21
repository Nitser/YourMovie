package com.test.your_movie.database.repository

import com.test.your_movie.database.dao.UserDao
import com.test.your_movie.database.entity.User

class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
//    val allWords: LiveData<List<Word>> = userDao.getAlphabetizedWords()

    fun insert(user: User) {
        userDao.insert(user)
    }

    fun findByLogin(login: String): User? {
        return userDao.findByLogin(login)
    }

}