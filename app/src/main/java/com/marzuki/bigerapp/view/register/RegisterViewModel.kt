package com.marzuki.bigerapp.view.register

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.marzuki.bigerapp.data.model.ErrorResponse
import com.marzuki.bigerapp.data.model.RegisterResponse
import com.marzuki.bigerapp.data.network.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RegisterViewModel: ViewModel() {
    fun register(username: String, email: String, password: String): Flow<RegisterResponse> = flow {
        try {
            val response = ApiConfig.getApiService().register(username, email, password)
            emit(response)
        } catch (e: Exception) {
            val errorMessage: String? = when (e) {
                is HttpException -> {
                    try {
                        val jsonInString = e.response()?.errorBody()?.string()
                        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                        errorBody.message
                    } catch (ex: Exception) {
                        ex.message
                    }
                }
                else -> e.message
            }
            emit(RegisterResponse(error = true, message = errorMessage ?: "Terjadi kesalahan"))
        }
    }.catch { e ->
        // Tangani kesalahan pada level flow
        emit(RegisterResponse(error = true, message = e.message ?: "Terjadi kesalahan"))
    }
}
