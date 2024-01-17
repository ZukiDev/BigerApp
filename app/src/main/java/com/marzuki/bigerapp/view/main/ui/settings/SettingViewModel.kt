package com.marzuki.bigerapp.view.main.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marzuki.bigerapp.data.model.GetUserPreferencesResponse
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.pref.DataModel
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.data.repository.DataRepository
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingViewModel(
    private val apiServicePost: ApiServicePost,
    private val userRepository: UserRepository,
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _userPreferencesState = MutableStateFlow<GetUserPreferencesResponse?>(null)
    val userPreferencesState: StateFlow<GetUserPreferencesResponse?> = _userPreferencesState

    fun saveData(data: DataModel) {
        viewModelScope.launch {
            dataRepository.saveData(data)
        }
    }

    suspend fun getUserPreferences(formatted: String = "true") =
        withContext(Dispatchers.IO) {
            val token = userRepository.getUserToken()
            Log.d("TokenSettingActivity", "$token")
            val response = apiServicePost.getUserPreferences("Bearer $token", formatted)

            if (response.isSuccessful) {
                _userPreferencesState.value = response.body()
            } else {
                // Handle error
                _userPreferencesState.value = null
            }
            response
        }
}