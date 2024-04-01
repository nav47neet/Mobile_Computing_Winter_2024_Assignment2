# Assignment 2: Weather App

## Overview

This project implements an application to fetch and display weather data. The `WeatherData` composable function is responsible for rendering the weather-related UI elements and handling data fetching.

## Dependencies
Retrofit: Used for making HTTP requests to fetch weather data from the OpenWeatherMap API.<br>
Room Database: Used for storing weather data locally.

## Methodology
### State Initialization: 
Initializes state variables for weather and location data, city name, latitude, longitude, and start date.
### UI Elements: 
Renders text fields for entering city name and start date, along with a button to fetch weather data.
### Button Click Handling: 
Handles button click to fetch weather data from the OpenWeatherMap API based on the entered city name and date.
### Temperature Data Processing: 
Processes the retrieved temperature data to calculate maximum and minimum temperatures.
### Displaying Weather Data: 
Displays weather information using the WeatherApp composable function. Additionally, saves weather data to the local Room database asynchronously.

