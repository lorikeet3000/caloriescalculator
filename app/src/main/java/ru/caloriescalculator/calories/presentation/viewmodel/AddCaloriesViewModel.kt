package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.core.DateConverter
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.event.AddCaloriesEvent
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddCaloriesViewModel @Inject constructor(
    private val repository: CaloriesRepository,
    private val dateConverter: DateConverter
) : ViewModel() {

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

    fun onCaloriesFor100Updated(value: String) {
        caloriesFor100Value = value
    }

    fun onFoodWeightUpdated(value: String) {
        foodWeightValue = value
    }

    fun onFoodNameValueChanged(value: String) {
        foodNameValue = value
    }

    fun onCaloriesFor100SubmitClicked() {
        val caloriesFor100 = caloriesFor100Value.toIntOrNull()
        val weight = foodWeightValue.toIntOrNull()
        if (foodNameValue.isEmpty() || caloriesFor100 == null || weight == null) {
            // todo show error
            return
        }
        saveCalories(foodNameValue, caloriesFor100, weight)
    }

    private fun saveCalories(foodName: String, caloriesFor100: Int, weight: Int) {
        viewModelScope.launch {
            val dateString = dateConverter.convertToString(Date())
            val entity = CaloriesEntity(
                date = dateString,
                foodName = foodName,
                caloriesFor100 = caloriesFor100,
                weight = weight
            )
            repository.addCalories(entity)

            // todo show dialog
        }
    }
}