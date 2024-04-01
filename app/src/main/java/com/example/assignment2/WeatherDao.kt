package com.example.assignment2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao{

    @Insert(onConflict = androidx.room.OnConflictStrategy.Companion.IGNORE)
    suspend fun  addWeather(weather:Weather)


    @Query("Select * from 'weather-table' where cityName = :cityName AND date=:date")
    fun getWeather(cityName:String,date:String): Flow<List<Weather>>


}

