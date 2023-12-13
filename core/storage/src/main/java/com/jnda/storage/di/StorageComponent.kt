package com.jnda.storage.di

import android.content.Context
import androidx.room.Room
import com.jnda.storage.dao.UserDAO
import com.jnda.storage.dao.WeatherDAO
import com.jnda.storage.db.AppRoomDatabase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.jnda.storage")
class StorageComponent {
    @Single
    fun createDatabase(context: Context) : AppRoomDatabase =
        Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            "app_database"
        ).build()

    @Single
    fun provideUserDao(db: AppRoomDatabase) : UserDAO =
        db.userDao()

    @Single
    fun provideWeatherDao(db: AppRoomDatabase) : WeatherDAO =
        db.weatherDao()
}