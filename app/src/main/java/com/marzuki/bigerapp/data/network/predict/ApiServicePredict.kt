package com.marzuki.bigerapp.data.network.predict

import com.marzuki.bigerapp.data.model.BusinessRecommendationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServicePredict {
    @FormUrlEncoded
    @POST("business")
    suspend fun getBusinessRecommendations(
        @Field("formattedAddress") formattedAddress: String,
        @Field("price") price: String,
        @Field("rating") rating: String
    ): List<BusinessRecommendationResponse>
}