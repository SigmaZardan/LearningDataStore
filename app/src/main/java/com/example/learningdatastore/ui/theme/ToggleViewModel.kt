package com.example.learningdatastore.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learningdatastore.LearningDataStoreApplication
import com.example.learningdatastore.data.UserPreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToggleViewModel(private val userPreferenceRepository: UserPreferenceRepository) :
    ViewModel() {
    companion object {
        const val TIMEOUT_MILLIS = 5_000L

        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LearningDataStoreApplication)
                ToggleViewModel(application.container.userPreferenceRepository)
            }
        }
    }

    val toggleUiState: StateFlow<ToggleUiState> =
        userPreferenceRepository.isDarkTheme.map { ToggleUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ToggleUiState()
            )

    fun saveToggledValue(isDarkTheme: Boolean) {
        viewModelScope.launch {
            userPreferenceRepository.saveToggledValue(isDarkTheme)
        }
    }


}


data class ToggleUiState(
    val isDarkTheme: Boolean = false
)