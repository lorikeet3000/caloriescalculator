package ru.caloriescalculator.calories.presentation.model

import java.util.Date

data class CaloriesItem(
    val date: Date,
    val calories: Int,
    val name: String,
    val weight: Int = 0
)

data class DayCaloriesItem(
    val meals: List<CaloriesItem>,
    val date: Date
) {

    val dayCalories: Int
        get() {
            return meals.sumOf {
                it.calories
            }
        }
}