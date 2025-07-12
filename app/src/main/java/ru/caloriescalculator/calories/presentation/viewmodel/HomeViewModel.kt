package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.core.DateConverter
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.mapper.CaloriesMapper
import ru.caloriescalculator.calories.presentation.model.HomeScreenState
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CaloriesRepository,
    private val mapper: CaloriesMapper,
    private val dateConverter: DateConverter
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            val todayDateString = dateConverter.convertToString(Date())
            repository.getForDate(todayDateString).collect { items ->
                _uiState.value = _uiState.value.copy(
                    items = mapper.mapEntitiesToItems(items)
                )
            }
        }
    }
}