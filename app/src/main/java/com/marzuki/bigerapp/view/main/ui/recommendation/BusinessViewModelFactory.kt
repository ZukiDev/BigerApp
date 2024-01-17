package com.marzuki.bigerapp.view.main.ui.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marzuki.bigerapp.data.network.predict.ApiServicePredict
import com.marzuki.bigerapp.data.repository.DataRepository
import com.marzuki.bigerapp.data.repository.UserRepository


class BusinessViewModelFactory(
    private val apiService: ApiServicePredict,
    private val userRepository: UserRepository,
    private val dataRepository: DataRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(apiService, userRepository, dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}