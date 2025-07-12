package ru.caloriescalculator.calories.presentation.model

import java.util.Date

data class CaloriesItem(
    val date: Date,
    val caloriesFor100: Int,
    val foodName: String,
    val weight: Int = 0
)