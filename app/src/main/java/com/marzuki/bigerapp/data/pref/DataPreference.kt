package com.marzuki.bigerapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class DataPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveData(user: DataModel) {
        dataStore.edit { preferences ->
            preferences[FORMATTED_ADDRESS] = user.formattedAddress
        }
    }

    fun getData(): Flow<DataModel> {
        return dataStore.data.map { preferences ->
            DataModel(
                preferences[FORMATTED_ADDRESS] ?: ""
            )
        }
    }

    suspend fun logoutData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataPreference? = null

        private val FORMATTED_ADDRESS = stringPreferencesKey("formattedAddress")

        fun getInstance(dataStore: DataStore<Preferences>): DataPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = DataPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
