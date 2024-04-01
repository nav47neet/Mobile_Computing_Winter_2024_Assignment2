package com.example.assignment2

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherData(database:WeatherDB) {
    var temperatureData by remember { mutableStateOf<TemperatureData?>(null) }
    var locationData by remember {
        mutableStateOf<LocationData?>(null)
    }
    // Define the latitude, longitude, start date, end date, and hourly parameter
    var cityName by remember {
        mutableStateOf("")
    }
    var latitude by remember {
        mutableStateOf("")
    }
    var longitude by remember {
        mutableStateOf("")
    }
    var startDate by remember {
        mutableStateOf("")
    }

    val endDate = startDate // Example end date
    val hourly = "temperature_2m" // Example hourly parameter
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text(text = "CityName") }
        )
//        OutlinedTextField(
//            value = longitude,
//            onValueChange = { longitude = it },
//            label = { Text(text = "Longitude") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )
        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text(text = "Date (YYYY-MM-DD)") },
        )
        Button(onClick = {
            // Make the network call inside the button click
            LocationService.getLocation(cityName, "json", "e926d836bd474871a3abcaa3d49c23a1")
                .enqueue(object : Callback<LocationData>{
                    override fun onResponse(
                        call: Call<LocationData>,
                        response: Response<LocationData>
                    ) {
                        if(response.isSuccessful){
                            locationData = response.body()
                            val result = locationData?.results?.firstOrNull()
                            longitude = result?.lon?.toString() ?: ""
                            latitude = result?.lat?.toString() ?: ""
//                            val jsonString = response.body().toString()
//                            val jsonObject = JSONObject(jsonString)
//                            val results = jsonObject.getJSONArray("results")
//                            if(results.length()>0){
//                                val firstResult = results.getJSONObject(0)
//                                longitude = firstResult.getDouble("lon").toString()
//                                latitude = firstResult.getDouble("lat").toString()
//                            }
                            if(latitude.isNotBlank() && longitude.isNotBlank()){
                                WeatherService.getTemperature(
                                    latitude.toDouble(), longitude.toDouble(), startDate, endDate, hourly
                                ).enqueue(object : Callback<TemperatureData> {
                                    override fun onResponse(
                                        call: Call<TemperatureData>,
                                        response: Response<TemperatureData>
                                    ) {
                                        if (response.isSuccessful) {
                                            temperatureData = response.body()



                                        } else {
                                            println("Error: ${response.code()}")
                                        }
                                    }

                                    override fun onFailure(call: Call<TemperatureData>, t: Throwable) {
                                        println("Failed to connect to the API: ${t.message}")
                                    }
                                })
                            }
                        }else{
                            println("Error: ${response.code()}")
                        }

                    }

                    override fun onFailure(call: Call<LocationData>, t: Throwable) {
                        println("Failed to connect to Location API: ${t.message}")
                    }
                })
//            WeatherService.getTemperature(
//                latitude.toDouble(), longitude.toDouble(), startDate, endDate, hourly
//            ).enqueue(object : Callback<TemperatureData> {
//                override fun onResponse(
//                    call: Call<TemperatureData>,
//                    response: Response<TemperatureData>
//                ) {
//                    if (response.isSuccessful) {
//                        temperatureData = response.body()
//                    } else {
//                        println("Error: ${response.code()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<TemperatureData>, t: Throwable) {
//                    println("Failed to connect to the API: ${t.message}")
//                }
//            })
        }) {
            Text("Fetch Weather Data")
        }
    }

    // Display the temperature data
    if (temperatureData != null) {
        val temperatureList = temperatureData?.hourly?.temperature_2m
        val maxTemp = temperatureList?.maxOrNull()
        val minTemp = temperatureList?.minOrNull()
        WeatherApp(temperatureData)
        GlobalScope.launch {
            database.weatherDao.addWeather(Weather(0,cityName,maxTemp.toString(),minTemp.toString()))
        }

    }
}