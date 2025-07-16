package ru.caloriescalculator.calories.core

object CaloriesCalculator {

    fun calculateTotalCalories(
        caloriesFor100: Int,
        weight: Int,
    ): Int {
        val caloriesFor1 = caloriesFor100.toDouble() / 100.0
        val caloriesForWeight = caloriesFor1 * weight.toDouble()
        return caloriesForWeight.toInt()
    }
}