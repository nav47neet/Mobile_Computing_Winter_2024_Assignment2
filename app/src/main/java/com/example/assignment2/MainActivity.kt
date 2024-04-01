package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.RoomDatabase
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
import com.example.assignment2.ui.theme.Assignment2Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    lateinit var database: WeatherDB

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(applicationContext,WeatherDB::class.java)
        setContent {
            Assignment2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
//
                    MyApp(database)
                }
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(temperatureData: TemperatureData?) {
    val openAlertDialog = remember { mutableStateOf(false) }
    temperatureData?.let {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val temperatureList = temperatureData?.hourly?.temperature_2m
            val maxTemp = temperatureList?.maxOrNull()
            val minTemp = temperatureList?.minOrNull()
            Dialog(onDismissRequest = { openAlertDialog.value = false }) {
                Text(text = "Min Temp ${minTemp}")
                Text(text = "Max Temo ${maxTemp}")
                
            }
        }
    }
}


@Composable
fun MyApp(database: WeatherDB) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "firstscreen") {
        composable("firstscreen") {
            FirstScreen(
                navigationToWeatherData = { navController.navigate("secondscreen") },
                navigationToWeatherDatabase = { navController.navigate("thirdscreen") }
            )
        }
        composable("secondscreen") {
            WeatherData(database)
        }
        composable("thirdscreen") {

        }
    }
}
