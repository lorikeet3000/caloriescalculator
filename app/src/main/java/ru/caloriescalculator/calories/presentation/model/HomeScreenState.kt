package ru.caloriescalculator.calories.presentation.model

import kotlin.math.max

data class HomeScreenState(
    val items: List<CaloriesItem> = emptyList<CaloriesItem>(),
    val date: String = "",
    val todayTotalCalories: Int = 0,
) {

    val dayCalories: Int
        get() {
            return items.sumOf {
                it.caloriesFor100
            }
        }

    val remainingCalories: Int
        get() {
            return max(0, todayTotalCalories - dayCalories)
        }
}