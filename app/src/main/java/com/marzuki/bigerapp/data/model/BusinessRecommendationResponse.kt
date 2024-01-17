package com.marzuki.bigerapp.data.model
data class BusinessRecommendationResponse(
    val price: String,
    val longitude: Double,
    val latitude: Double,
    val rating: Float,
    val kategori: String,
    val Restaurant: String,
    val url: String,
    val id: String
)
data class BusinessRecommendationRequest(
    val formattedAddress: String,
    val price: String,
    val rating: String
)