package com.jnda.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jnda.storage.data.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDAO {

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} ORDER BY date_time DESC")
    fun getWeathers() : Flow<List<WeatherEntity>>

    @Insert
    suspend fun insert(weatherEntity: WeatherEntity)
}