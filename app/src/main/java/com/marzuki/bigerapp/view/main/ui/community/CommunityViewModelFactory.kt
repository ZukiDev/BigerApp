package com.marzuki.bigerapp.view.main.ui.community

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marzuki.bigerapp.data.network.post.ApiConfigPost
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.repository.UserRepository
import com.marzuki.bigerapp.di.Injection

class CommunityViewModelFactory(
    private val apiServicePost: ApiServicePost,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommunityViewModel(apiServicePost, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: CommunityViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): CommunityViewModelFactory {
            return INSTANCE ?: synchronized(CommunityViewModelFactory::class.java) {
                val apiServicePost = ApiConfigPost.getApiServicePost()
                val userRepository = Injection.provideUserRepository(context)
                INSTANCE ?: CommunityViewModelFactory(apiServicePost, userRepository).also { INSTANCE = it }
            }
        }
    }
}
