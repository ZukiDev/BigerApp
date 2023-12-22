package com.marzuki.bigerapp.view.main.ui.add

import androidx.lifecycle.ViewModel
import com.marzuki.bigerapp.data.model.AddUserPreferencesRequest
import com.marzuki.bigerapp.data.model.GetUserPreferencesResponse
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class AddViewModel(
    private val userRepository: UserRepository,
    private val apiServicePost: ApiServicePost
) : ViewModel() {

    private val _userPreferencesState = MutableStateFlow<GetUserPreferencesResponse?>(null)
    val userPreferencesState: StateFlow<GetUserPreferencesResponse?> = _userPreferencesState

    suspend fun addUserPreferences(requestBody: AddUserPreferencesRequest) =
        withContext(Dispatchers.IO) {
            val token = userRepository.getUserToken()
            apiServicePost.addUserPreferences("Bearer $token", requestBody)
        }

    suspend fun getUserPreferences(formatted: Boolean = false) =
        withContext(Dispatchers.IO) {
            val token = userRepository.getUserToken()
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
