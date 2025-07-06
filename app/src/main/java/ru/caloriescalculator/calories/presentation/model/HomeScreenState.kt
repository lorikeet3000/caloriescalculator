package ru.caloriescalculator.calories.presentation.model

import java.util.Date
import kotlin.math.max

data class HomeScreenState(
    val calories: DayCaloriesItem = DayCaloriesItem(
        meals = emptyList(),
        date = Date()
    ),
    val todayTotalCalories: Int = 0,
) {

    val remainingCalories: Int
        get() {
            return max(0, todayTotalCalories - calories.dayCalories)
        }
}