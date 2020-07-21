package com.test.your_movie.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey @ColumnInfo(name = "login") val login: String,
        @ColumnInfo(name = "password") val password: ByteArray,
        @ColumnInfo(name = "salt") val salt: ByteArray,
        @ColumnInfo(name = "iv") val iv: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (!password.contentEquals(other.password)) return false

        return true
    }

    override fun hashCode(): Int {
        return password.contentHashCode()
    }
}