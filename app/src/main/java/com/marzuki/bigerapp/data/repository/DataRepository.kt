package com.marzuki.bigerapp.data.repository

import com.marzuki.bigerapp.data.pref.DataModel
import com.marzuki.bigerapp.data.pref.DataPreference
import kotlinx.coroutines.flow.Flow

class DataRepository private constructor(
    private val dataPreference: DataPreference
) {

    suspend fun saveData(user: DataModel) {
        dataPreference.saveData(user)
    }

    fun getData(): Flow<DataModel> {
        return dataPreference.getData()
    }

    suspend fun logoutData() {
        dataPreference.logoutData()
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            dataPreference: DataPreference
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(dataPreference)
            }.also { instance = it }
    }
}