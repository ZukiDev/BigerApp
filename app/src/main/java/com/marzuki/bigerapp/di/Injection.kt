package com.marzuki.bigerapp.di

import android.content.Context
import com.marzuki.bigerapp.data.network.post.ApiConfigPost
import com.marzuki.bigerapp.data.pref.DataPreference
import com.marzuki.bigerapp.data.pref.UserPreference
import com.marzuki.bigerapp.data.pref.dataStore
import com.marzuki.bigerapp.data.repository.DataRepository
import com.marzuki.bigerapp.data.repository.PostRepository
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideDataRepository(context: Context): DataRepository {
        val pref = DataPreference.getInstance(context.dataStore)
        return DataRepository.getInstance(pref)
    }
}
