package com.example.learningdatastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


interface AppContainer {
    val userPreferenceRepository: UserPreferenceRepository
}

private const val DARK_THEME_PREFERENCE_NAME = "dark_theme_preference"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DARK_THEME_PREFERENCE_NAME)

class DefaultAppContainer(context: Context) : AppContainer {
    override val userPreferenceRepository: UserPreferenceRepository =
        UserPreferenceRepository(dataStore = context.dataStore)
}