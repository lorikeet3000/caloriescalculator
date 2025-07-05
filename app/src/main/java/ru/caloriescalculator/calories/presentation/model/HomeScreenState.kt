package ru.caloriescalculator.calories.presentation.model

data class HomeScreenState(
    val calories: List<DayCaloriesItem> = emptyList<DayCaloriesItem>()
) {

    fun getAverageCalories(forDays: Int): Int {
        return calories.takeLast(forDays)
            .sumOf {
                it.dayCalories
            } / forDays
    }
}