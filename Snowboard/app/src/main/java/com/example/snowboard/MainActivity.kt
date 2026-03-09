package com.example.snowboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.databinding.ActivityMainBinding
import com.example.snowboard.fragments.HistoryScreenFragment
import com.example.snowboard.fragments.MainScreenFragment
import com.example.snowboard.fragments.SkiSlopesScreenFragment
import com.example.snowboard.fragments.TipsScreenFragment
import com.example.snowboard.fragments.VideosScreenFragment
import com.example.snowboard.fragments.WeatherScreenFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        containerViewFragments()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bottomNavigation()
    }

    private fun containerViewFragments() {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun bottomNavigation() {
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home", R.drawable.ic_home)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "History", R.drawable.ic_history)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Tips", R.drawable.ic_tips)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(4, "Ski Slopes", R.drawable.ic_slopes)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(5, "Videos", R.drawable.ic_video)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(6, "Weather", R.drawable.ic_weather)
        )

        binding.bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> replaceFragment(MainScreenFragment())
                2 -> replaceFragment(HistoryScreenFragment())
                3 -> replaceFragment(TipsScreenFragment())
                4 -> replaceFragment(SkiSlopesScreenFragment())
                5 -> replaceFragment(VideosScreenFragment())
                6 -> replaceFragment(WeatherScreenFragment())
            }
        }

        //default fragment
        replaceFragment(MainScreenFragment())
        binding.bottomNavigation.show(2)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navHostFragment, fragment)
            .commit()
    }
}