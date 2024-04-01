package com.example.assignment2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather-table")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    @ColumnInfo(name = "Location")
    val cityName:String,
    @ColumnInfo(name = "Minimum Temp")
    val minTemp: String,
    @ColumnInfo(name = "Maximum Temp")
    val maxTemp:String,
    @ColumnInfo(name = "Date")
    val date:String
)