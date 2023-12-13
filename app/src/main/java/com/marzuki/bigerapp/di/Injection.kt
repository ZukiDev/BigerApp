package com.marzuki.bigerapp.di

import android.content.Context
import com.marzuki.bigerapp.data.pref.UserPreference
import com.marzuki.bigerapp.data.pref.dataStore
import com.marzuki.bigerapp.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}