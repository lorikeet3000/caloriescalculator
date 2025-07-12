package ru.caloriescalculator.calories.presentation.model

import java.util.Date

data class CaloriesItem(
    val date: Date,
    val caloriesFor100: Int,
    val foodName: String,
    val weight: Int = 0
) {

    val totalCalories: Int
        get() {
            val caloriesFor1 = caloriesFor100.toDouble() / 100.0
            val caloriesForWeight = caloriesFor1 * weight.toDouble()
            return caloriesForWeight.toInt()
        }
}