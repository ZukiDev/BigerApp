package com.marzuki.bigerapp.view.main.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marzuki.bigerapp.data.network.post.ApiConfigPost
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.repository.DataRepository
import com.marzuki.bigerapp.data.repository.UserRepository
import com.marzuki.bigerapp.di.Injection

class SettingViewModelFactory(
    private val apiServicePost: ApiServicePost,
    private val userRepository: UserRepository,
    private val dataRepository: DataRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingViewModel(apiServicePost, userRepository, dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): SettingViewModelFactory {
            return INSTANCE ?: synchronized(SettingViewModelFactory::class.java) {
                val apiServicePost = ApiConfigPost.getApiServicePost()
                val userRepository = Injection.provideUserRepository(context)
                val dataRepository = Injection.provideDataRepository(context)
                INSTANCE ?: SettingViewModelFactory(apiServicePost, userRepository, dataRepository).also { INSTANCE = it }
            }
        }
    }
}
