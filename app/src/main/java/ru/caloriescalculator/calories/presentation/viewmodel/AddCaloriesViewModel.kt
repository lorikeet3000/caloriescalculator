package ru.caloriescalculator.calories.presentation.viewmodel

import android.text.format.DateFormat
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.event.AddCaloriesEvent
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

    var caloriesFor100Value by mutableStateOf("")
        private set

    var foodWeightValue by mutableStateOf("")
        private set

    val foodNameHasErrors by derivedStateOf {
        foodNameValue.isEmpty()
    }

    fun onEvent(event: AddCaloriesEvent) {
        when (event) {
            AddCaloriesEvent.CaloriesFor100Submit -> onCaloriesFor100SubmitClicked()
            is AddCaloriesEvent.CaloriesFor100Update -> onCaloriesFor100Updated(event.newValue)
            AddCaloriesEvent.CaloriesSubmit -> onCaloriesSubmitClicked()
            is AddCaloriesEvent.CaloriesValueUpdate -> onCalorieValueUpdated(event.newValue)
            is AddCaloriesEvent.FoodNameChange -> onFoodNameValueChanged(event.newValue)
            is AddCaloriesEvent.FoodWeightUpdate -> onFoodWeightUpdated(event.newValue)
        }

    }

    fun onCalorieValueUpdated(value: String) {
        caloriesValue = value
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

    fun onCaloriesSubmitClicked() {
        if (foodNameValue.isEmpty()) {
            return
        }
        val caloriesInt = caloriesValue.toIntOrNull()
        if (caloriesInt == null) {
            return
        }
        viewModelScope.launch {
            val dateString = DateFormat.format("dd.MM.yyyy", Date()).toString()
            val entity = CaloriesEntity(
                date = dateString,
                foodName = foodNameValue,
                calories = caloriesInt
            )
            repository.addCalories(entity)
        }
    }

    fun onCaloriesFor100SubmitClicked() {
        val caloriesInt = caloriesFor100Value.toIntOrNull()
        val weight = foodWeightValue.toDoubleOrNull()
        // todo calculate
    }
}