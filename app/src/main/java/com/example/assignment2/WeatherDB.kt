package com.example.assignment2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Weather::class], version = 1)
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

}