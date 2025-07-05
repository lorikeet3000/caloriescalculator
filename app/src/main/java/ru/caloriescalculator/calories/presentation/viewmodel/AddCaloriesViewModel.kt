package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddCaloriesViewModel @Inject constructor(
    private val repository: CaloriesRepository
) : ViewModel() {

    var foodNameValue by mutableStateOf("")
        private set

    var caloriesValue by mutableStateOf("")
        private set

    var caloriesFor100 by mutableStateOf("")
        private set

    var weightValue by mutableStateOf("")
        private set

    fun onCalorieValueUpdated(value: String) {
        caloriesValue = value
    }

    fun onCaloriesFor100Updated(value: String) {
        caloriesFor100 = value
    }

    fun onWeightUpdated(value: String) {
        weightValue = value
    }

    fun onFoodNameValueChanged(value: String) {
        foodNameValue = value
    }

    fun onCaloriesSubmitClicked() {
        if (foodNameValue.isEmpty()) {
            // todo show error
            return
        }
        val caloriesInt = caloriesValue.toIntOrNull()
        if (caloriesInt == null) {
            // todo show error
            return
        }
        viewModelScope.launch {
            repository.addCalories(CaloriesItem(
                date = Date(),
                calories = caloriesInt,
                name = foodNameValue
            ))
        }
    }

    fun onCaloriesFor100SubmitClicked() {
        val caloriesInt = caloriesFor100.toIntOrNull()
        val weight = weightValue.toDoubleOrNull()
        // todo calculate
    }
}