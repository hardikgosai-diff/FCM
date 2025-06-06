package com.app.fcm.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Delete
    suspend fun delete(user: User)
}
