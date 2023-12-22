package com.marzuki.bigerapp.data.model

data class AddUserPlaceTypesRequest(
    val update: String? = null,
    val types: List<String>
)

data class AddUserPlaceTypesResponse(
    val success: Boolean,
    val placesTypes: List<String>
)