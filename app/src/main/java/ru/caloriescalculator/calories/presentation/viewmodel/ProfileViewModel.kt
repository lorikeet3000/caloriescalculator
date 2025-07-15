package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.event.ProfileEvent
import ru.caloriescalculator.calories.presentation.model.ProfileScreenState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: CaloriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenState())
    val uiState: StateFlow<ProfileScreenState> = _uiState.asStateFlow()

    var totalCaloriesValue by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            repository.getTotalCalories().collect { totalCalories ->
                totalCaloriesValue = totalCalories.toString()
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnTotalCaloriesChanged -> onTotalCaloriesChanged(event.value)
        }
    }

    private fun onTotalCaloriesChanged(value: String) {
        totalCaloriesValue = value
        val totalCaloriesInt = value.toIntOrNull()
        _uiState.value = _uiState.value.copy(
            isTotalCaloriesError = totalCaloriesInt == null
        )
        viewModelScope.launch {
            repository.saveTotalCalories(totalCaloriesInt ?: 0)
        }
    }
}