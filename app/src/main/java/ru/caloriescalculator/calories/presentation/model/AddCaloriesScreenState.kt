package ru.caloriescalculator.calories.presentation.model

data class AddCaloriesScreenState(
    val isFoodNameError: Boolean = false,
    val isCaloriesError: Boolean = false,
    val isWeightError: Boolean = false,
    val confirmDialogState: AddCaloriesConfirmDialogState? = null,
    val evaluatedCalories: Int? = null
)