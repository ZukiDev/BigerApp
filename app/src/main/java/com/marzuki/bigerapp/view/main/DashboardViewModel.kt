package com.marzuki.bigerapp.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marzuki.bigerapp.data.model.LogoutResponse
import com.marzuki.bigerapp.data.model.Post
import com.marzuki.bigerapp.data.network.ApiConfig
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.data.repository.PostRepository
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: UserRepository
) : ViewModel() {
    init {
        Log.d("DashboardViewModel", "ViewModel initialized")
    }

    private val _logoutResultFlow = MutableStateFlow<LogoutResponse?>(null)
    val logoutResultFlow: StateFlow<LogoutResponse?> = _logoutResultFlow.asStateFlow()

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

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