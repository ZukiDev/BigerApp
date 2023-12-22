package com.marzuki.bigerapp.data.model

data class GetUserPreferencesResponse(
	val success: Boolean,
	val preferences: Preferences?
)

data class AddressComponents(
	val country_code: String?,
	val address: String?,
	val route: String?,
	val administrative_area_level_5: String?,
	val administrative_area_level_4: String?,
	val administrative_area_level_3: String?,
	val administrative_area_level_2: String?,
	val administrative_area_level_1: String?,
	val postal_code: Int?
)

data class Preferences(
	val addressComponents: AddressComponents?,
	val formattedAddress: String?,
	val placeTypes: List<String>?
)

