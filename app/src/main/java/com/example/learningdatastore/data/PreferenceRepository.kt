package com.example.learningdatastore.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferenceRepository(private val dataStore: DataStore<Preferences>) {

    // define key
    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        const val TAG = "UserPreferenceRepo"
    }

    suspend fun saveToggledValue(isDarkTheme: Boolean) {
        dataStore.edit { preference -> preference[IS_DARK_THEME] = isDarkTheme }
    }

    val isDarkTheme: Flow<Boolean> = dataStore.data.catch {
        if (it is IOException) {
            Log.d(TAG, "Error reading preferences", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preference ->
        preference[IS_DARK_THEME] ?: false
    }
}