package ru.caloriescalculator.calories.presentation.event

sealed interface ProfileEvent {

    data class OnTotalCaloriesChanged(
        val value: String
    ) : ProfileEvent
}