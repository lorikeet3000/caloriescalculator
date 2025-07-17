package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.core.CaloriesCalculator
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.event.AddCaloriesEvent
import ru.caloriescalculator.calories.presentation.mapper.CaloriesMapper
import ru.caloriescalculator.calories.presentation.model.AddCaloriesConfirmDialogState
import ru.caloriescalculator.calories.presentation.model.AddCaloriesScreenState
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddCaloriesViewModel @Inject constructor(
    private val repository: CaloriesRepository,
    private val mapper: CaloriesMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddCaloriesScreenState())

    val uiState: StateFlow<AddCaloriesScreenState> = _uiState.asStateFlow()

    var foodNameValue by mutableStateOf("")
        private set

    var caloriesFor100Value by mutableStateOf("")
        private set

    var foodWeightValue by mutableStateOf("")
        private set

    fun onEvent(event: AddCaloriesEvent) {
        when (event) {
            AddCaloriesEvent.CaloriesFor100Submit -> onCaloriesFor100SubmitClicked()
            is AddCaloriesEvent.CaloriesFor100Update -> onCaloriesFor100Updated(event.newValue)
            is AddCaloriesEvent.FoodNameChange -> onFoodNameValueChanged(event.newValue)
            is AddCaloriesEvent.FoodWeightUpdate -> onFoodWeightUpdated(event.newValue)
        }
    }

    private fun onCaloriesFor100Updated(value: String) {
        caloriesFor100Value = value
        _uiState.value = _uiState.value.copy(
            isCaloriesError = false
        )
        evaluateCalories()
    }

    private fun onFoodWeightUpdated(value: String) {
        foodWeightValue = value
        _uiState.value = _uiState.value.copy(
            isWeightError = false
        )
        evaluateCalories()
    }

    private fun onFoodNameValueChanged(value: String) {
        foodNameValue = value
        _uiState.value = _uiState.value.copy(
            isFoodNameError = false
        )
    }

    private fun evaluateCalories() {
        val caloriesFor100 = caloriesFor100Value.toIntOrNull()
        val weight = foodWeightValue.toIntOrNull()
        val evaluatedCalories = if (caloriesFor100 != null && caloriesFor100 != 0 && weight != null) {
            CaloriesCalculator.calculateTotalCalories(
                caloriesFor100 = caloriesFor100,
                weight = weight,
            )
        } else {
            0
        }
        _uiState.value = _uiState.value.copy(evaluatedCalories = evaluatedCalories)
    }

    private fun onCaloriesFor100SubmitClicked() {
        val caloriesFor100 = caloriesFor100Value.toIntOrNull()
        val weight = foodWeightValue.toIntOrNull()
        if (foodNameValue.isEmpty()) {
            _uiState.value = _uiState.value.copy(isFoodNameError = true)
            return
        }
        if (caloriesFor100 == null) {
            _uiState.value = _uiState.value.copy(isCaloriesError = true)
            return
        }
        if (weight == null) {
            _uiState.value = _uiState.value.copy(isWeightError = true)
            return
        }
        saveCalories(foodNameValue, caloriesFor100, weight)
    }

    private fun saveCalories(foodName: String, caloriesFor100: Int, weight: Int) {
        viewModelScope.launch(IO) {
            val item = CaloriesItem(
                date = Date(),
                caloriesFor100 = caloriesFor100,
                foodName = foodName,
                weight = weight
            )
            val entity = mapper.mapItemToEntity(item)
            repository.addCalories(entity)
            _uiState.value = _uiState.value.copy(
                confirmDialogState = AddCaloriesConfirmDialogState(
                    foodName = foodName,
                    calories = item.totalCalories
                )
            )
        }
    }
}