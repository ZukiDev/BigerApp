package com.marzuki.bigerapp.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marzuki.bigerapp.data.model.LoginResponse
import com.marzuki.bigerapp.data.network.ApiConfig
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    suspend fun login(email: String, password: String): LoginResponse? {
        try {
            return ApiConfig.getApiService().login(email, password)
        } catch (e: Exception) {
            // Handle error, misalnya tampilkan pesan kesalahan
            e.printStackTrace()
            return null
        }
    }
}