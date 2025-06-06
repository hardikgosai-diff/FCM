package com.app.fcm.room

import com.app.fcm.di.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun getUser() : User = apiService.getUser()

    suspend fun insert(user: User) = userDao.insert(user)

    suspend fun delete(user: User) = userDao.delete(user)
}
