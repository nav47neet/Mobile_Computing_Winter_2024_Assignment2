package com.example.assignment2

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder().baseUrl("https://archive-api.open-meteo.com/v1/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val WeatherService:ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("archive")
    fun getTemperature(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("hourly") hourly: String
    ): Call<TemperatureData>
}