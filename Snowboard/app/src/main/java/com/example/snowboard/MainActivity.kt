package com.example.snowboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.snowboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Setup NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set Toolbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // 1. Open Drawer when BottomAppBar menu icon is clicked
        binding.bottomAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // 2. Handle Drawer Item Clicks
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> navController.navigate(R.id.mainScreenFragment)
                R.id.history -> navController.navigate(R.id.historyScreenFragment)
                R.id.tips -> navController.navigate(R.id.tipsScreenFragment)
                R.id.skiSlopes -> navController.navigate(R.id.skiSlopesScreenFragment)
                R.id.videos -> navController.navigate(R.id.videosScreenFragment)
                R.id.weather -> navController.navigate(R.id.weatherScreenFragment)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Hide BottomAppBar and FAB when on Splash Screen
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashScreenFragment) {
                binding.bottomAppBar.visibility = View.GONE
                binding.fab.visibility = View.GONE
                supportActionBar?.hide()
            } else {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE
                supportActionBar?.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // This allows the drawer hamburger icon to work with the NavController
        return NavigationUI.navigateUp(
            navController,
            binding.drawerLayout
        ) || super.onSupportNavigateUp()
    }
}