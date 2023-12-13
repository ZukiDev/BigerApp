package com.marzuki.bigerapp.data.network

import com.marzuki.bigerapp.data.model.LoginResponse
import com.marzuki.bigerapp.data.model.LogoutResponse
import com.marzuki.bigerapp.data.model.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): LogoutResponse
}