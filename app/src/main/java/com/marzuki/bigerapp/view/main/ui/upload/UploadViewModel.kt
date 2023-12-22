package com.marzuki.bigerapp.view.main.ui.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.marzuki.bigerapp.data.ResultState
import com.marzuki.bigerapp.data.model.AddPostResponse
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.data.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UploadViewModel(
    private val userRepository: UserRepository,
    private val apiServicePost: ApiServicePost
) : ViewModel() {

    private val _uploadResult = MutableLiveData<ResultState<String>>()
    val uploadResult: LiveData<ResultState<String>> get() = _uploadResult

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
    fun uploadImage(token: String, image: File, title: String, text: String) = liveData {
        emit(ResultState.Loading)

        val tokenn = "Bearer $token"
        Log.e("UploadViewModel", "Token: $tokenn")

        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val textPart = text.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            "post_pict",
            image.name,
            requestImageFile
        )

        Log.e("UploadViewModel", "Image: $image")
        Log.e("UploadViewModel", "Title Part: $titlePart")
        Log.e("UploadViewModel", "Text Part: $textPart")

        try {
            val successResponse = apiServicePost.addPost(tokenn, multipartBody, titlePart, textPart)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddPostResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

}