package ru.caloriescalculator.calories.presentation.model

data class HomeScreenState(
    val calories: List<CaloriesItem> = emptyList<CaloriesItem>()
)