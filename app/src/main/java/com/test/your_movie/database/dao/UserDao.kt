package com.test.your_movie.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.test.your_movie.database.entity.User

@Dao
interface UserDao {
//    @Query("SELECT * FROM user")
//    fun getAll(): List<User>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE login LIKE :login LIMIT 1")
    fun findByLogin(login: String): User?

    @Insert
    fun insert(users: User)

    @Delete
    fun delete(user: User)

}