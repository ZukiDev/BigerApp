package com.marzuki.bigerapp.view

import android.content.Context
import com.marzuki.bigerapp.data.network.post.ApiConfigPost
import com.marzuki.bigerapp.data.repository.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.repository.PostRepository
import com.marzuki.bigerapp.di.Injection
import com.marzuki.bigerapp.view.login.LoginViewModel
import com.marzuki.bigerapp.view.main.DashboardViewModel
import com.marzuki.bigerapp.view.main.ui.add.AddViewModel
import com.marzuki.bigerapp.view.main.ui.upload.UploadViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val apiServicePost: ApiServicePost,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(userRepository, apiServicePost) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(userRepository, apiServicePost) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                val userRepository = Injection.provideUserRepository(context)
                val apiServicePost = ApiConfigPost.getApiServicePost()
                INSTANCE ?: ViewModelFactory(userRepository, apiServicePost).also { INSTANCE = it }
            }
        }
    }
}