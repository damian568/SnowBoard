package com.example.snowboard.Fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.snowboard.Constants.Constants
import com.example.snowboard.Interface.WeatherApiService
import com.example.snowboard.R
import com.example.snowboard.Response.WeatherResponse
import com.example.snowboard.User.Settings.AppPreferences
import com.example.snowboard.databinding.FragmentMainScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val currentLang = Locale.getDefault().language
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val weatherService by lazy { retrofit.create(WeatherApiService::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMenu.setOnClickListener { openDrawer() }
        binding.btnNotification.setOnClickListener { showNoNotifications() }
        binding.txtQuickActionsViewAll.setOnClickListener { openDrawer() }
        binding.txtNearbySlopesViewAll.setOnClickListener { goToSkiSlopes() }
        loadUserName()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationPermission()
    }

    private fun openDrawer() {
        requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
            ?.openDrawer(GravityCompat.START)
    }

    private fun showNoNotifications() {
        Toast.makeText(requireContext(), R.string.no_new_notifications, Toast.LENGTH_SHORT).show()
    }

    private fun goToSkiSlopes() {
        findNavController().navigate(R.id.skiSlopesScreenFragment)
    }

    private fun loadUserName() {
        val user = auth.currentUser ?: return

        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (!isAdded) return@addOnSuccessListener
                val fullName = document.getString("fullName")
                if (!fullName.isNullOrBlank()) {
                    binding.txtUserName.text = getString(R.string.home_user_name_format, fullName)
                }
            }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndFetchWeather()
            } else {
                Log.e("Home", getString(R.string.weather_error))
            }
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

        fusedLocationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            if (location != null) {
                fetchWeatherData(location.latitude, location.longitude)
            } else {
                fusedLocationClient.lastLocation.addOnSuccessListener { lastLoc ->
                    lastLoc?.let { fetchWeatherData(it.latitude, it.longitude) }
                }
            }
        }
    }

    private fun fetchWeatherData(lat: Double, lon: Double) {
        val useMetric = AppPreferences.isMetricUnits(requireContext())
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = weatherService.getCurrentWeather(
                    lat,
                    lon,
                    Constants.API_KEY,
                    if (useMetric) "metric" else "imperial",
                    currentLang
                )
                if (isAdded) updateWeatherUI(response, useMetric)
            } catch (e: Exception) {
                Log.e("Home", "Error: ${e.message}")
            }
        }
    }

    private fun updateWeatherUI(data: WeatherResponse, useMetric: Boolean) {
        val tempUnit = if (useMetric) "C" else "F"
        val condition = data.weather[0].main
        binding.apply {
            txtTemp.text = "${data.main.temp.toInt()}°$tempUnit"
            txtCondition.text = data.weather[0].description.replaceFirstChar { it.uppercase() }
            txtLocationHome.text = data.name
            imgWeatherIcon.setImageResource(iconForCondition(condition))
        }
    }

    private fun iconForCondition(condition: String): Int {
        return when (condition) {
            "Clear", "Sunny" -> R.drawable.ic_weather_sun
            "Rain", "Drizzle" -> R.drawable.ic_weather_rain
            "Snow", "Squall" -> R.drawable.ic_weather_snow
            "Thunderstorm" -> R.drawable.ic_weather_storm
            else -> R.drawable.ic_cloud
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}
