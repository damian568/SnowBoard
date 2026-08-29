package com.example.snowboard.User.Settings

import android.content.Context

object AppPreferences {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_METRIC_UNITS = "metric_units"
    private const val KEY_NOTIFICATIONS = "notifications_enabled"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isDarkMode(context: Context) = prefs(context).getBoolean(KEY_DARK_MODE, false)

    fun setDarkMode(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun isMetricUnits(context: Context) = prefs(context).getBoolean(KEY_METRIC_UNITS, true)

    fun setMetricUnits(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_METRIC_UNITS, enabled).apply()
    }

    fun isNotificationsEnabled(context: Context) = prefs(context).getBoolean(KEY_NOTIFICATIONS, true)

    fun setNotificationsEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply()
    }
}
