package com.example.snowboard.Lists

import com.example.snowboard.R

enum class QuickAction(
    val id: String,
    val icon: Int,
    val label: Int,
    val destination: Int
) {
    SKI_SLOPES("skiSlopes", R.drawable.ic_slopes, R.string.menu_ski_slopes, R.id.skiSlopesScreenFragment),
    EQUIPMENT("equipment", R.drawable.ic_equipment, R.string.menu_equipment, R.id.equipmentScreenFragment),
    TIPS("tips", R.drawable.ic_tips, R.string.menu_tips, R.id.tipsScreenFragment),
    VIDEOS("videos", R.drawable.ic_video, R.string.menu_videos, R.id.videosScreenFragment),
    WEATHER("weather", R.drawable.ic_weather, R.string.menu_weather, R.id.weatherScreenFragment),
    HISTORY("history", R.drawable.ic_history, R.string.menu_history, R.id.historyScreenFragment);

    companion object {
        fun fromId(id: String) = entries.find { it.id == id }
    }
}
