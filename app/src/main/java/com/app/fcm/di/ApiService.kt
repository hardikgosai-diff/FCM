package com.app.fcm.di

import com.app.fcm.room.User
import retrofit2.http.GET

interface ApiService {

    @GET("9fea911e-71ee-4977-9985-270bef5999c2")
    suspend fun getUser(): User

}