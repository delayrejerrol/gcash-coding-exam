package com.jnda.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jnda.storage.dao.UserDAO
import com.jnda.storage.dao.WeatherDAO
import com.jnda.storage.data.entity.UserEntity
import com.jnda.storage.data.entity.WeatherEntity

@Database(
    entities = [
        UserEntity::class,
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun weatherDao(): WeatherDAO
}