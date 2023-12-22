package com.marzuki.bigerapp.data.model

data class UpdateUserPreferencesRequest(
	val address: String? = null,
	val route: String? = null,
	val administrative_area_level_5: String? = null,
	val administrative_area_level_4: String? = null,
	val administrative_area_level_3: String? = null,
	val administrative_area_level_2: String? = null,
	val administrative_area_level_1: String? = null,
	val postal_code: Int? = null
)

data class UpdateUserPreferencesResponse(
	val country_code: String,
	val address: String,
	val administrative_area_level_2: String,
	val administrative_area_level_3: String,
	val administrative_area_level_1: String,
	val administrative_area_level_5: String,
	val postal_code: Int,
	val route: String,
	val administrative_area_level_4: String
)