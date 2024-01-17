package com.marzuki.bigerapp.view.main.ui.recommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marzuki.bigerapp.data.model.BusinessRecommendationRequest
import com.marzuki.bigerapp.data.model.BusinessRecommendationResponse
import com.marzuki.bigerapp.data.network.predict.ApiServicePredict
import com.marzuki.bigerapp.data.pref.DataModel
import com.marzuki.bigerapp.data.repository.DataRepository
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class BusinessState {
    data class Success(val recommendations: List<BusinessRecommendationResponse>) : BusinessState()
    data class Error(val message: String) : BusinessState()
    object Loading : BusinessState()
}

class BusinessViewModel(
    private val apiService: ApiServicePredict,
    private val userRepository: UserRepository,
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _businessState = MutableLiveData<BusinessState>()
    val businessState: LiveData<BusinessState> get() = _businessState

    fun getData(): LiveData<DataModel> {
        return dataRepository.getData().asLiveData()
    }

    fun fetchBusinessRecommendations(request: BusinessRecommendationRequest) {
        _businessState.value = BusinessState.Loading

        viewModelScope.launch {
            try {
//                val token = userRepository.getUserToken()
//                Log.e("BusinessViewModel", "Token: $token")
                Log.e("BusinessViewModel", "Request: $request")

                val recommendations = apiService.getBusinessRecommendations(
                    formattedAddress = request.formattedAddress,
                    price = request.price,
                    rating = request.rating
                )

                _businessState.value = BusinessState.Success(recommendations)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("BusinessViewModel", "HTTP error: ${e.code()}, Response: $errorBody")
                _businessState.value = BusinessState.Error("HTTP error: ${e.code()}, Response: $errorBody")
            } catch (e: Exception) {
                Log.e("BusinessViewModel", "Error fetching business recommendations: ${e.message}")
                _businessState.value = BusinessState.Error("Error fetching business recommendations: ${e.message}")
            }
        }
    }
}

