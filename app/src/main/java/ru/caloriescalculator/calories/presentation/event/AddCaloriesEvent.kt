package ru.caloriescalculator.calories.presentation.event

sealed interface AddCaloriesEvent {

    data class FoodNameChange(
        val newValue: String
    ) : AddCaloriesEvent

    data class CaloriesValueUpdate(
        val newValue: String
    ) : AddCaloriesEvent

    data object CaloriesSubmit : AddCaloriesEvent

    data class CaloriesFor100Update(
        val newValue: String
    ) : AddCaloriesEvent

    data class FoodWeightUpdate(
        val newValue: String
    ) : AddCaloriesEvent

    data object CaloriesFor100Submit : AddCaloriesEvent

}