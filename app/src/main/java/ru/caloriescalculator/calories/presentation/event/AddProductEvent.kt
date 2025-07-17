package ru.caloriescalculator.calories.presentation.event

sealed interface AddProductEvent {

    data class FoodNameChange(
        val newValue: String
    ) : AddProductEvent

    data class CaloriesFor100Update(
        val newValue: String
    ) : AddProductEvent

    data class FoodWeightUpdate(
        val newValue: String
    ) : AddProductEvent

    data class CommentUpdate(
        val newWalue: String
    ) : AddProductEvent

    data object ProductDataSubmit : AddProductEvent
}