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
            preferences[ADDRESS] = user.address
            preferences[ROUTE] = user.route
            preferences[LEVEL_5] = user.administrative_area_level_5
            preferences[LEVEL_4] = user.administrative_area_level_4
            preferences[LEVEL_3] = user.administrative_area_level_3
            preferences[LEVEL_2] = user.administrative_area_level_2
            preferences[LEVEL_1] = user.administrative_area_level_1
            preferences[POSTAL_CODE] = user.postal_code
        }
    }

    fun getData(): Flow<DataModel> {
        return dataStore.data.map { preferences ->
            DataModel(
                preferences[ADDRESS] ?: "",
                preferences[ROUTE] ?: "",
                preferences[LEVEL_5] ?: "",
                preferences[LEVEL_4] ?: "",
                preferences[LEVEL_3] ?: "",
                preferences[LEVEL_2] ?: "",
                preferences[LEVEL_1] ?: "",
                preferences[POSTAL_CODE] ?: 0,
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

        private val ADDRESS = stringPreferencesKey("address")
        private val ROUTE = stringPreferencesKey("route")
        private val LEVEL_5 = stringPreferencesKey("dusun")
        private val LEVEL_4 = stringPreferencesKey("desa")
        private val LEVEL_3 = stringPreferencesKey("kecamatan")
        private val LEVEL_2 = stringPreferencesKey("city")
        private val LEVEL_1 = stringPreferencesKey("provinsi")
        private val POSTAL_CODE = intPreferencesKey("postalCode")

        fun getInstance(dataStore: DataStore<Preferences>): DataPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = DataPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
