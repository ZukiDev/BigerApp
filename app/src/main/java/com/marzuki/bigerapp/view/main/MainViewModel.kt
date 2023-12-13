package com.marzuki.bigerapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marzuki.bigerapp.data.model.LogoutResponse
import com.marzuki.bigerapp.data.network.ApiConfig
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _logoutResultFlow = MutableStateFlow<LogoutResponse?>(null)
    val logoutResultFlow: StateFlow<LogoutResponse?> = _logoutResultFlow.asStateFlow()

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    // Gunakan fungsi ini jika tidak memerlukan token
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    // Gunakan fungsi ini jika memerlukan token
    fun logoutWithToken(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().logout("Bearer $token")
                _logoutResultFlow.value = response
            } catch (e: Exception) {
                // Handle error, misalnya tampilkan pesan kesalahan
                e.printStackTrace()
                _logoutResultFlow.value = LogoutResponse(success = false, message = "Terjadi kesalahan", loggedStatus = true)
            }
        }
    }
}