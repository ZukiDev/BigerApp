package com.marzuki.bigerapp.data.repository

import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getUserToken(): String {
        val userModel = userPreference.getSession().first()
        return userModel.token
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference).also { instance = it }
            }
        }
    }
}
