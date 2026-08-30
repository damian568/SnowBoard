package com.example.snowboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.snowboard.User.Settings.AppPreferences
import com.example.snowboard.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(
            if (AppPreferences.isDarkMode(this)) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Setup NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set Toolbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, binding.drawerLayout)

        drawerMenu()
        handleDrawerMenuClicks()
        handleDrawerHeaderClick()
        navControllerDestinationChanged()
    }

    private fun drawerMenu() {
        // 1. Open Drawer when BottomAppBar menu icon is clicked
        binding.bottomAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun handleDrawerMenuClicks() {
        // 2. Handle Drawer Item Clicks
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> navController.navigate(R.id.mainScreenFragment)
                R.id.history -> navController.navigate(R.id.historyScreenFragment)
                R.id.tips -> navController.navigate(R.id.tipsScreenFragment)
                R.id.equipment -> navController.navigate(R.id.equipmentScreenFragment)
                R.id.skiSlopes -> navController.navigate(R.id.skiSlopesScreenFragment)
                R.id.videos -> navController.navigate(R.id.videosScreenFragment)
                R.id.weather -> navController.navigate(R.id.weatherScreenFragment)
                R.id.logout -> logOut()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun handleDrawerHeaderClick() {
        // 3. Navigate to Profile when the header's chevron button is tapped
        val headerView = binding.navView.getHeaderView(0)
        headerView.findViewById<View>(R.id.btn_nav_header_profile).setOnClickListener {
            navController.navigate(R.id.profileScreenFragment)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        navController.navigate(
            R.id.loginScreenFragment,
            null,
            androidx.navigation.NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build()
        )
    }

    private fun navControllerDestinationChanged() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            behaviorBottomAppBar(destination)
        }
    }

    private fun behaviorBottomAppBar(destination: NavDestination) {
        when (destination.id) {
            R.id.splashScreenFragment, R.id.mainScreenFragment, R.id.tipDetailFragment, R.id.loginScreenFragment, R.id.registerScreenFragment, R.id.profileScreenFragment, R.id.personalInformationFragment, R.id.settingsFragment, R.id.helpSupportFragment, R.id.changePasswordScreenFragment, R.id.changeEmailScreenFragment -> {
                binding.bottomAppBar.visibility = View.GONE
                binding.fab.hide()
            }

            R.id.historyScreenFragment, R.id.equipmentScreenFragment -> {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.bottomAppBar.performShow()
                //binding.fab.hide() // Hide ONLY the FAB
            }

            else -> {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.fab.hide() //Need to change it to show
                /**
                 *  When I make the user Interface need this fab to make it show
                 *  and to add it only to some of the screens
                 */
                binding.bottomAppBar.performShow()
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