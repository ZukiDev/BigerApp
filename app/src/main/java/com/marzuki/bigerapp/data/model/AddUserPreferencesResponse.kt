package com.marzuki.bigerapp.data.model

data class AddUserPreferencesRequest(
	val address: String,
	val route: String,
	val administrative_area_level_5: String,
	val administrative_area_level_4: String,
	val administrative_area_level_3: String,
	val administrative_area_level_2: String,
	val administrative_area_level_1: String,
	val postal_code: Int
)

data class AddUserPreferencesResponse(
	val success: Boolean,
	val preferences: UserPreferences
)

data class UserPreferences(
	val address: String,
	val route: String,
	val administrative_area_level_5: String,
	val administrative_area_level_4: String,
	val administrative_area_level_3: String,
	val administrative_area_level_2: String,
	val administrative_area_level_1: String,
	val postal_code: Int,
	val country_code: String
)
