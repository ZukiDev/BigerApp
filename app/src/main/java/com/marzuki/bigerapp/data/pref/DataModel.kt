package com.marzuki.bigerapp.data.pref

data class DataModel(
    val address: String,
    val route: String,
    val administrative_area_level_5: String,
    val administrative_area_level_4: String,
    val administrative_area_level_3: String,
    val administrative_area_level_2: String,
    val administrative_area_level_1: String,
    val postal_code: Int
)