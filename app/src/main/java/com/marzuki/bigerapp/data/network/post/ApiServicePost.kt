package com.marzuki.bigerapp.data.network.post

import com.marzuki.bigerapp.data.model.AddPostResponse
import com.marzuki.bigerapp.data.model.AddUserPlaceTypesRequest
import com.marzuki.bigerapp.data.model.AddUserPlaceTypesResponse
import com.marzuki.bigerapp.data.model.AddUserPreferencesRequest
import com.marzuki.bigerapp.data.model.AddUserPreferencesResponse
import com.marzuki.bigerapp.data.model.GetUserPreferencesResponse
import com.marzuki.bigerapp.data.model.LikePostResponse
import com.marzuki.bigerapp.data.model.Post
import com.marzuki.bigerapp.data.model.PostCollectionResponse
import com.marzuki.bigerapp.data.model.UpdatePostRequest
import com.marzuki.bigerapp.data.model.UpdatePostResponse
import com.marzuki.bigerapp.data.model.UpdateUserPreferencesRequest
import com.marzuki.bigerapp.data.model.UpdateUserPreferencesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface ApiServicePost {
    @POST("preferences")
    suspend fun addUserPreferences(
        @Header("Authorization") token: String,
        @Body requestBody: AddUserPreferencesRequest
    ): Response<AddUserPreferencesResponse>

    @PATCH("preferences")
    suspend fun updateUserPreferences(
        @Header("Authorization") token: String,
        @Body requestBody: UpdateUserPreferencesRequest
    ): Response<UpdateUserPreferencesResponse>

    @PUT("preferences/types")
    suspend fun addUserPlaceTypes(
        @Header("Authorization") token: String,
        @Query("update") update: String?,
        @Body requestBody: AddUserPlaceTypesRequest
    ): Response<AddUserPlaceTypesResponse>

    @GET("preferences")
    suspend fun getUserPreferences(
        @Header("Authorization") token: String,
        @Query("formatted") formatted: Boolean = false
    ): Response<GetUserPreferencesResponse>

    @Multipart
    @POST("posts")
    suspend fun addPost(
        @Header("Authorization") token: String,
        @Part post_pict: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("text") text: RequestBody
    ): AddPostResponse

    @PATCH("posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: String,
        @Header("Authorization") token: String,
        @Body requestBody: UpdatePostRequest
    ): Response<UpdatePostResponse>

    @PUT("posts/{postDocumentId}")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Path("postDocumentId") postDocumentId: String
    ): Response<LikePostResponse>

    @GET("posts")
    suspend fun getPosts(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): PostCollectionResponse
}