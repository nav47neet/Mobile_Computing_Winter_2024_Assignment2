package com.example.assignment2

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit2 = Retrofit.Builder().baseUrl("https://api.geoapify.com/v1/").addConverterFactory(GsonConverterFactory.create()).build()
val LocationService: ApiServiceLocation = retrofit2.create(ApiServiceLocation::class.java)



interface ApiServiceLocation {
    @GET("geocode/search")
    fun getLocation(
        @Query("text")cityName:String,
        @Query("format")format:String,
        @Query("apiKey")apiKey:String
    ): Call<LocationData>

}