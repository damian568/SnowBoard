package com.example.snowboard.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.snowboard.Interface.WeatherApiService
import com.example.snowboard.R
import com.example.snowboard.Response.WeatherResponse
import com.example.snowboard.databinding.FragmentWeatherScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherScreenFragment : Fragment() {
    private lateinit var binding: FragmentWeatherScreenBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val API_KEY: String = "84dfac113d43bc77a052c4e6ec5edbb2"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else {
            getLocationAndFetchWeather()
        }
    }

    private fun getLocationAndFetchWeather() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkLocationPermission()
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                fetchWeatherData(location.latitude, location.longitude)
            } else {
                Log.e("Weather", "Location is null. Ensure GPS is enabled.")
            }
        }
    }

    private fun fetchWeatherData(lat: Double, lon: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherApiService::class.java)

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = service.getCurrentWeather(lat, lon, API_KEY)
                updateUI(response)
            } catch (e: Exception) {
                Log.e("Weather", "Error: ${e.message}")
            }
        }
    }

    private fun updateUI(data: WeatherResponse) {
        binding.apply {
            date.text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
            myLocation.text = "${data.name}"
            temp.text = "${data.main.temp.toInt()}°C"
            minTemp.text = "Min: ${data.main.temp_min.toInt()}°C"
            maxTemp.text = "Max: ${data.main.temp_max.toInt()}°C"
            humidity.text = "${data.main.humidity}%"
            windSpeed.text = "${data.wind.speed} m/s"
            conditions.text = data.weather[0].main
            sunrise.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(data.sys.sunrise * 1000))
            sunset.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(data.sys.sunset * 1000))
            sea.text = "${data.main.sea_level} hPa"
            updateTheBackUI(conditions.text.toString())
        }
    }

    private fun updateTheBackUI(conditions: String) {
        binding.apply {
            when (conditions) {
                "Clouds" -> {
                    weather.text = "Clouds"
                    weatherBack.setBackgroundResource(R.drawable.mist_back)
                    weatherAnimation.setAnimation(R.raw.clouds)
                }

                "Windy" -> {
                    weather.text = "Windy"
                    weatherBack.setBackgroundResource(R.drawable.mist_back)
                    weatherAnimation.setAnimation(R.raw.windy)
                }

                "Rain" -> {
                    weather.text = "Rain"
                    weatherBack.setBackgroundResource(R.drawable.rain_back)
                    weatherAnimation.setAnimation(R.raw.cloudy_rain)
                }

                "Snow" -> {
                    weather.text = "Snow"
                    weatherBack.setBackgroundResource(R.drawable.snow_back)
                    weatherAnimation.setAnimation(R.raw.snow)
                }

                "Clear" -> {
                    weather.text = "Clear"
                    weatherBack.setBackgroundResource(R.drawable.sunny_back)
                    weatherAnimation.setAnimation(R.raw.sunny)
                }

                "Sunny" -> {
                    weather.text = "Sunny"
                    weatherBack.setBackgroundResource(R.drawable.sunny_back)
                    weatherAnimation.setAnimation(R.raw.sunny)
                }

                "Haze" -> {
                    weather.text = "Haze"
                    weatherBack.setBackgroundResource(R.drawable.mist_back)
                    weatherAnimation.setAnimation(R.raw.mist)
                }

                "Squall" -> {
                    weather.text = "Squall"
                    weatherBack.setBackgroundResource(R.drawable.snow_back)
                    weatherAnimation.setAnimation(R.raw.snow)
                }

                "Drizzle" -> {
                    weather.text = "Drizzle"
                    weatherBack.setBackgroundResource(R.drawable.rain_back)
                    weatherAnimation.setAnimation(R.raw.cloudy_rain)
                }

                "Thunderstorm" -> {
                    weather.text = "Thunderstorm"
                    weatherBack.setBackgroundResource(R.drawable.storm_back)
                    weatherAnimation.setAnimation(R.raw.storm)
                }

                else -> {
                    weather.text = "Clear"
                    weatherBack.setBackgroundResource(R.drawable.sunny_back)
                    weatherAnimation.setAnimation(R.raw.sunny)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // To HIDE the toolbar
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        // To SHOW the toolbar when leaving this fragment
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}