package com.example.snowboard.Response

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val sys: Sys,
    val name: String
)

data class Main(
    val temp: Double,
    val humidity: Int,
    val pressure: Int,
    val temp_min: Double,
    val temp_max: Double,
    val sea_level: Int
)

data class Weather(val main: String, val description: String)
data class Wind(val speed: Double)
data class Sys(val sunrise: Long, val sunset: Long, val country: String?)