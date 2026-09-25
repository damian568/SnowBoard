package com.example.snowboard.Lists

data class SkiSlopesList(
    val imgResort: Int,
    val title: String,
    val description: String,
    val imgSkiSlopes: Int,
    val link: String,
    var isExpanded: Boolean = false
)
