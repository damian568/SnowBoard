package com.example.snowboard.User.Settings

import android.content.Context

object AppPreferences {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_METRIC_UNITS = "metric_units"
    private const val KEY_NOTIFICATIONS = "notifications_enabled"
    private const val KEY_QUICK_ACTIONS = "quick_actions"
    private const val DEFAULT_QUICK_ACTIONS = "skiSlopes,equipment,tips,videos"
    const val MAX_QUICK_ACTIONS = 4

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

    fun getQuickActions(context: Context): List<String> {
        val stored = prefs(context).getString(KEY_QUICK_ACTIONS, DEFAULT_QUICK_ACTIONS)
        return stored?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
    }

    fun setQuickActions(context: Context, ids: List<String>) {
        prefs(context).edit().putString(KEY_QUICK_ACTIONS, ids.take(MAX_QUICK_ACTIONS).joinToString(",")).apply()
    }
}
