package com.example.snowboard.Lists

data class TipsList(
    val title: String,
    val description: String,
    val imageResource: Int,
    var isExpanded: Boolean = false
)
