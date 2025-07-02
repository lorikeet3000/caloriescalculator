package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.caloriescalculator.calories.data.LocalDataSource
import ru.caloriescalculator.calories.presentation.model.HomeScreenState

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    init {
        _uiState.value = HomeScreenState(
            calories = LocalDataSource.testCalories
        )
    }
}