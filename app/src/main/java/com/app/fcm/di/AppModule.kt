package com.app.fcm.di

import android.content.Context
import androidx.room.Room
import com.app.fcm.room.AppDatabase
import com.app.fcm.room.UserDao
import com.app.fcm.room.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 06/06/25.
 *
 * @author hardkgosai.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/") // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    fun provideUserRepository(api: ApiService, dao: UserDao): UserRepository {
        return UserRepository(api, dao)
    }

}